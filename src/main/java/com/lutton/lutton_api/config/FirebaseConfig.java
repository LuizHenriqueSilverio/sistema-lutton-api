package com.lutton.lutton_api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.service-account.path}")
    private String serviceAccountPath;

    @Value("${firebase.database.url}")
    private String databaseUrl;

    private FirebaseApp firebaseApp;

    @PostConstruct
    public void initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccountStream = new ClassPathResource(serviceAccountPath).getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .setDatabaseUrl(databaseUrl)
                    .build();
            this.firebaseApp = FirebaseApp.initializeApp(options);
        } else {
            this.firebaseApp = FirebaseApp.getInstance();
        }
    }

    @Bean
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore(this.firebaseApp);
    }

    @Bean
    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance(this.firebaseApp);
    }
}

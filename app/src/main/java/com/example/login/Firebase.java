package com.example.login;

import com.google.firebase.firestore.FirebaseFirestore;
public class Firebase {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FirebaseFirestore getFirestoreInstance() {
        return db;
    }
}

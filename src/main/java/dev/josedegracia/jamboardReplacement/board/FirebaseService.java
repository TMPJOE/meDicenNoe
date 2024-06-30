package dev.josedegracia.jamboardReplacement.board;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {


    // Método para guardar un Board en Firestore y devolver el objeto Board
    public Board saveBoard(Board board) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection("boards").document(String.valueOf(board.getId())).set(board);
        // Puedes usar future.get() para asegurarte de que la operación se completa antes de devolver el objeto
        future.get();
        return board;
    }

    // Método para obtener un Board de Firestore
    public Board getBoard(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.DocumentSnapshot> future = db.collection("boards").document(String.valueOf(id)).get();
        return future.get().toObject(Board.class);
    }

    // Método para subir una imagen a Firebase Storage
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        StorageClient.getInstance().bucket().create(fileName, file.getBytes(), file.getContentType());
        String mediaLink = StorageClient.getInstance().bucket().get(fileName).getMediaLink();
        Map<String, String> result = new HashMap<>();
        result.put("url", mediaLink);
        return result;
    }
}
package com.example.quemaduras_ia.screens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quemaduras_ia.R;
import com.example.quemaduras_ia.tensorflow_model.TFLiteModelHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InicioFragment extends Fragment {
    //Variables
    private CardView cardView1, cardView2;
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_IMAGE_PICK = 101;
    private TFLiteModelHandler TFLiteModelHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        cardView1 = view.findViewById(R.id.card1_inicio);
        cardView2 = view.findViewById(R.id.card2_inicio);

        //Inicializa el "TFLiteModelHandler"
        try {
            TFLiteModelHandler = new TFLiteModelHandler(getActivity().getAssets(), "modeloV2.tflite");
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    abrirCamara();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        return view;
    }

    //Capturar una imagen con la cámara
    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Seleccionar una imagen de la galería
    private void abrirGaleria() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Obtener la imagen capturada por la cámara
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    // Procesa la imagen para la predicción
                    procesarImagen(imageBitmap);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                // Imagen seleccionada de la galería
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        //Obtiene la URI de la imagen seleccionada
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                        // Procesa la imagen seleccionada
                        procesarImagen(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void procesarImagen(Bitmap bitmap) {
        //Si bitmap es "null", el método retorna inmediatamente
        if (bitmap == null) return;

        //Redimensiona la imagen a 224x224 píxeles
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
        //Almacenar los píxeles de la imagen
        int[] intValues = new int[224 * 224];
        //Array para la entrada del modelo
        float[][][][] input = new float[1][224][224][3];
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());

        //Valores RGB de cada píxel a un rango de [0, 1]
        for (int i = 0; i < 224; i++) {
            for (int j = 0; j < 224; j++) {
                int pixel = intValues[i * 224 + j];
                input[0][i][j][0] = ((pixel >> 16) & 0xFF) / 255.0f;
                input[0][i][j][1] = ((pixel >> 8) & 0xFF) / 255.0f;
                input[0][i][j][2] = (pixel & 0xFF) / 255.0f;
            }
        }

        if (TFLiteModelHandler != null) {
            //Realiza una predicción y guarda la salida
            float[] output = TFLiteModelHandler.predict(input);
            //Log.d("Salida del modelo", "Valores de salida: " + Arrays.toString(output));

            int predictedClass = -1;
            float maxProbability = -1.0f;

            //Encuentra la clase con la mayor probabilidad en la salida
            for (int i = 0; i < output.length; i++) {
                if (output[i] > maxProbability) {
                    maxProbability = output[i];
                    predictedClass = i;
                }
            }

            //Mapear índices a nombres de clases
            String[] classNames = {"primer grado", "segundo grado", "tercer grado"};

            Bundle bundle = new Bundle();
            if (predictedClass >= 0 && predictedClass < classNames.length) {
                //Log.d("Predicción", "Predicción: " + classNames[predictedClass] + " con probabilidad: " + maxProbability);
                bundle.putString("prediccion", classNames[predictedClass]);
            } else {
                //Log.e("Predicción", "Clase desconocida");
                bundle.putString("prediccion", "clase desconocida");
            }

            Uri imageUri = getImageUri(getContext(), resizedBitmap);
            bundle.putParcelable("imagen", imageUri);

            Navigation.findNavController(getView()).navigate(R.id.action_inicioFragment_to_analisisFragment, bundle);
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        //Directorio de caché del contexto de la aplicación
        File tempDir = context.getCacheDir();
        //Crea un nuevo archivo en el directorio de caché
        File tempFile = new File(tempDir, "tempImage.png");

        //Guardar el Bitmap en el archivo
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            //Comprime el Bitmap en formato PNG
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //Obtener la URI del archivo
        return FileProvider.getUriForFile(context, "com.example.quemaduras_ia.fileprovider", tempFile);
    }
}
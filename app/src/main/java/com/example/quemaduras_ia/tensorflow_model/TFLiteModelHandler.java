package com.example.quemaduras_ia.tensorflow_model;

import org.tensorflow.lite.Interpreter;
import java.nio.MappedByteBuffer;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

public class TFLiteModelHandler {
    //Variables
    private Interpreter tflite;

    //Constructor
    public TFLiteModelHandler(AssetManager assetManager, String modelPath) throws IOException {
        //Buscar el archivo del modelo en la ruta especificada por modelPath
        tflite = new Interpreter(loadModelFile(assetManager, modelPath));
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        //Se abre el descriptor de archivo
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        //Se crea un flujo de entrada para el descriptor de archivo
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        //Se obtiene el canal del archivo desde el flujo de entrada
        FileChannel fileChannel = inputStream.getChannel();
        //Desplazamiento inicial y la longitud declarada del archivo
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        //Mapea el archivo en memoria en modo de solo lectura
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float[] predict(float[][][][] input) {
        //Matriz de salida con dimensiones [1][3], asumiendo que el modelo predice 3 clases
        float[][] output = new float[1][3];
        //Ejecuta el modelo TFLite con la entrada input y guarda los resultados en output
        tflite.run(input, output);
        //Se devuelve la única fila de la matriz de salida, que contiene las predicciones
        return output[0];
    }
}

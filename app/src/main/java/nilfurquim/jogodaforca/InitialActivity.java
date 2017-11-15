package nilfurquim.jogodaforca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InitialActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView mImageView;
    Button pictureButton;
    Button audioButton;
    Button nextButton;
    Boolean isRecording = false;
    String PATH_NAME = "audio";
    MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        mImageView = (ImageView) findViewById(R.id.mImageView);
        pictureButton = (Button) findViewById(R.id.pictureButton);
//        audioButton = (Button) findViewById(R.id.audioButton);
        nextButton = (Button) findViewById(R.id.nextButton);

//        recorder = new MediaRecorder();

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startNextActivity();
            }
        });

        pictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

//        audioButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(!isRecording) {
//                    isRecording = true;
//                    try {
//                        startRecording();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    isRecording = true;
//                    stopRecording();
//                }
//            }
//        });
    }
    private void startNextActivity(){
        Intent intent = new Intent(this, Jogo.class);
        startActivity(intent);
    }
//    private void stopRecording() {
//        recorder.stop();
//        recorder.reset();
//        recorder.release();
//    }
//
//    private void startRecording() throws IOException {
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        recorder.setOutputFile(PATH_NAME);
//        recorder.prepare();
//        recorder.start();
//    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}

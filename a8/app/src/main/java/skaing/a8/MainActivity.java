package skaing.a8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import jforsythe.Message;
import jforsythe.MessageType;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private EditText txtInput;
    private EditText txtOutput;
    private String name;
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private ServerListener serverListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        this.txtInput = findViewById(R.id.txtInput);
        this.txtInput.setOnEditorActionListener(this);
        this.txtOutput = findViewById(R.id.txtOutput);

        getUserName();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        serverListener.running = false;
        try{
            objectOutputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            socket = new Socket("odin.cs.csub.edu", 3390);
            outputStream = socket.getOutputStream();
            outputStream.flush();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.flush();

            serverListener = new ServerListener(socket, txtOutput);
            serverListener.start();

            Message connect = new Message(MessageType.CONNECT, name, "HI from android");
            objectOutputStream.writeObject(connect);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Name");
        EditText userNameInput = new EditText(this);
        userNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(userNameInput);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = userNameInput.getText().toString();
                Log.d("USER_NAME", name);
                if(name.equals("")) getUserName();
                else connect();
            }
        });

        builder.show();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP) {
            Message tmp = new Message(MessageType.MESSAGE, name, txtInput.getText().toString());
            try {
                objectOutputStream.writeObject(tmp);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            txtInput.setText("");
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return true;
    }
}
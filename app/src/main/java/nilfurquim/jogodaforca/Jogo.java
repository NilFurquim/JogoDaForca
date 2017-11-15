package nilfurquim.jogodaforca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Jogo extends AppCompatActivity {
    TextView gameTextView;
    TextView countTextView;
    EditText inputEditText;

    String word;
    String dashes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        gameTextView = (TextView) findViewById(R.id.gameTextView);
        countTextView = (TextView) findViewById(R.id.countTextView);
        inputEditText = (EditText) findViewById(R.id.inputEditText);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String URL = "http://infinite-depths-57886.herokuapp.com/getWord";

        // GET
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JSONObject jsonRes = null;
                        try {
                            jsonRes = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            word = jsonRes.getString("word");
                            dashes = generateDashes(word.length());
                            gameTextView.setText(dashes);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                gameTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);

        inputEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                inputLetter(inputEditText.getText().toString());
                inputEditText.setText("");
            }
        });
    }

    private void inputLetter(String text) {
        StringBuilder builder = new StringBuilder(dashes);
        for (int i = 0; i < word.length();i++){
            if(text.charAt(0) == word.charAt(i)) {
                builder.setCharAt(i, text.toUpperCase().charAt(0));
            }
        }

        dashes = builder.toString();
        updateDashes();
    }

    private void updateDashes() {
        gameTextView.setText(dashes);
    }
    private String generateDashes(int size) {
        String dashes = new String();
        for (int i = 0; i < size; i++){
            dashes += "-";
        }
        return dashes;
    }


}

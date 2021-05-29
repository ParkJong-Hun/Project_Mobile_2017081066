package pjh.mjc.project_gimal_2017081066;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//회원가입창
public class UserCreateActivity extends AppCompatActivity {
    
    //선언
    DBHelper dbHelper;
    SQLiteDatabase db;

    EditText id, password;
    Button cancel, submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);

        //바인딩
        id = findViewById(R.id.id2);
        password = findViewById(R.id.pw2);
        dbHelper = new DBHelper(this);

        //아이디 입력창에 자동으로 포커스
        id.requestFocus();

        //취소
        cancel = (Button)findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
        //회원가입 정보 입력 완료
        submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //아이디 비밀번호 입력 값 받아오고 디비 켜기.
                String idInput = id.getText().toString();
                String pwInput = password.getText().toString();
                db = dbHelper.getWritableDatabase();
                //조건에 맞지 않으면 회원가입 안되게 하기
                //TODO: 회원가입할 때 중복된 아이디가 있는지 체크
                if(!(idInput.equals("") || pwInput.equals(""))) {
                    db.execSQL("INSERT INTO User VALUES ('" + idInput + "', '" + pwInput + "');");
                    db.close();

                    Intent submit_intent = new Intent(UserCreateActivity.this, LoginActivity.class);
                    submit_intent.putExtra("id", idInput);
                    submit_intent.putExtra("password", pwInput);
                    setResult(RESULT_OK, submit_intent);
                    finish();
                } else if (idInput.length() > 15 || pwInput.length() > 15){
                    Toast.makeText(getApplicationContext(), "15자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
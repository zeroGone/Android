package net.skhu.e07login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 로그인 액티비티를 호출할 때, 사용할 요청 식별 번호(request code) 이다.
    static final int RC_SIGN_IN = 337;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnLogout = findViewById(R.id.btnLogout);

        // 로그인 버튼이 클릭되면, 로그인 액티비티를 호출한다.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginInActivity();
            }
        });
        // 로그아웃 버튼이 클릭되면, 로그아웃 처리를 하고, 화면에 표시된 사용자 이름을 갱신한다.
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                setUserName();
            }
        });

        // MainActivity가 시작되자마자, 로그인 액티비티를 호출한다.
        startLoginInActivity();
    }

    // 로그인 액티비티를 호출하는 메소드이다.
    void startLoginInActivity() {
        // 이메일 인증과 구글 계정 인증을 사용하여 로그인 가능하도록 설정한다.
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // 로그인(sign in) 액티비티를 호출한다.
        // startActivityForResult 메소드는, 다른 액티비티를 호출할 때 사용하는 메소드이다.
        // startActivityForResult = 결과를 받기 위해서 액티비티를 호출한다는 뜻이다.
        // 어떤 액티비티를 호출하고, 그 액티비티 실행 결과를 전달받겠다는 뜻이다.
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN); // RC_SIGN_IN 다른 액티비티를 호출할 때, 호출 식별 번호이다.
    }

    // 화면에 현재 사용자 이름을 표시한다.
    void setUserName() {
        // 현재 사용자 객체를 구한다.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) { // 로그인하지 않았다면, null 이다.
            String s = "Authentication success." + user.getDisplayName();
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            textView.setText(s);
        } else {
            textView.setText("Anonymous");
        }
    }

    // startActivityForResult 메소드로 호출된 액티비티로부터 전달된 결과를 받기위한 메소드이다.
    // 파라미터 변수:
    //   requestCode: startActivityForResult 메소드를 호출할 때 전달한 호출 식별 번호이다.
    //   resultCode:  호출된 액티비티의 실행 결과 값이다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) { // 위에서 RC_SIGN_IN 호출 식별 번호로 startActvityForResult를
            // 호출한 결과이다.
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // 로그인 작업이 성공인 경우
                setUserName();
            } else {
                // 로그인 작업이 실패한 경우
                String message = "Authentication failure. " + response.getError().getErrorCode()
                        + " " + response.getError().getMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}

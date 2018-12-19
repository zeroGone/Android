package net.skhu.e06list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ItemList itemList;
    int selectedIndex;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    FirebaseDbService firebaseDbService;
    ItemEditDialogFragment itemEditDialogFragment; // 수정 대화상자 관리자

    // 로그인 액티비티를 호출할 때, 사용할 요청 식별 번호(request code) 이다.
    static final int RC_SIGN_IN = 337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 레이아웃 인플레이션

        initRecyclerView(); // 리사이클러뷰 초기화
    }

    // 로그인 액티비티를 호출하는 메소드이다.
    void startLoginInActivity() {
        // 이메일 인증과 구글 계정 인증을 사용하여 로그인 가능하도록 설정한다.
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // 로그인(sign in) 액티비티를 호출한다.
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN); // RC_SIGN_IN 다른 액티비티를 호출할 때, 호출 식별 번호이다.
    }

    // startActivityForResult 메소드로 호출된 액티비티로부터 전달된 결과를 받기위한 메소드이다.
    // 파라미터 변수:
    //   requestCode: startActivityForResult 메소드를 호출할 때 전달한 호출 식별 번호이다.
    //   resultCode:  호출된 액티비티의 실행 결과 값이다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) { // 로그인 액티비티 호출에 대한 결과이면
            IdpResponse response = IdpResponse.fromResultIntent(data);
            String msg = null;
            if (resultCode == RESULT_OK) {
                // 로그인 작업이 성공인 경우
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                msg = "Authentication success. " + user.getDisplayName();
            } else {
                // 로그인 작업이 실패한 경우
                msg = "Authentication failure. " + response.getError().getErrorCode()
                        + " " + response.getError().getMessage();
            }
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            invalidateOptionsMenu(); // 메뉴 다시 그리기
            initRecyclerView();
        }
    }

    // 리사이클러뷰 초기화 작업
    private void initRecyclerView() {
        itemList = new ItemList(); // 데이터 목록 객체 생성

        // 리사이클러 뷰 설정
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, itemList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myRecyclerViewAdapter);

        // firebase DB 서비스 생성
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = (user != null) ? user.getUid() : "anonymous";
        firebaseDbService = new FirebaseDbService(this,
                myRecyclerViewAdapter, itemList, userId);

        Button b = (Button)findViewById(R.id.btnAdd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String title = editText.getText().toString();
                editText.setText("");
                Item item = new Item(title);
                firebaseDbService.addIntoServer(item);
            }
        });
    }

    public void showItemEditDialog(int position) {
        if (itemEditDialogFragment == null) // 대화상자 관리자 객체를 아직 만들지 않았다면
            itemEditDialogFragment = new ItemEditDialogFragment(); // 대화상자 관리자 객체를 만든다
        selectedIndex = position; // 수정할 항목의 index를 대입한다.
        itemEditDialogFragment.show(getSupportFragmentManager(), "EditDialog"); // 화면에 대화상자 보이기
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_remove).setVisible(itemList.getCheckedCount() > 0);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        menu.findItem(R.id.action_logIn).setVisible(user == null);
        menu.findItem(R.id.action_logOut).setVisible(user != null);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_remove) {
            for (int i = itemList.size() - 1; i >= 0; --i)
                if (itemList.get(i).isChecked()) {
                    String key = itemList.getKey(i);
                    firebaseDbService.removeFromServer(key);
                }
            menuItem.setVisible(false);
            return true;
        } else if (id == R.id.action_logIn)
            startLoginInActivity();
        else if (id == R.id.action_logOut) {
            FirebaseAuth.getInstance().signOut();
            invalidateOptionsMenu(); // 메뉴 다시 그리기
            initRecyclerView();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}


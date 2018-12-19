package net.skhu.e06list;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;

// 데이터 항목을 수정하기 위한 대화상자 관리자 클래스
public class ItemEditDialogFragment extends DialogFragment {

    @Override
    // 수정 대화상자를 만드는 메소드.  이 메소드는 대화상자를 새로 만들어야 할 때에만 호출된다.
    // 한 번 만들어진 대화상자는 계속 재사용 된다.
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity activity = (MainActivity)getActivity();  // 액티비티 객체에 대한 참조 얻기
        final Item item = activity.itemList.get(activity.selectedIndex); // 선택된 항목 얻기
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("수정"); // 대화 상자의 제목 설정하기

        // 대화 상자에 표시될 뷰 객체들을 자동으로 생성함.
        final View rootView = activity.getLayoutInflater().inflate(R.layout.item_edit, null);

        // 자동으로 생성된 EditText 객체들에 대한 참조를 얻는다.
        final EditText editText_title = (EditText)rootView.findViewById(R.id.editText_title);
        final EditText editText_createDate = (EditText)rootView.findViewById(R.id.editText_createTime);

        // 데이터 항목의 내용을 EditText에 채운다.
        editText_title.setText(item.getTitle());
        editText_createDate.setText(item.getCreateTimeFormatted());

        // 자동으로 생성된 뷰 객체들을 대화상자에 추가한다
        builder.setView(rootView);

        // 대화상자에 '저장' 버튼 추가하는 코드의 시작
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            // 대화상자의 '저장' 버튼이 클릭되면 실행되는 메소드
            public void onClick(DialogInterface dialog, int which) {
                // 대화 상자의 EditText에 입력된 내용을 꺼낸다.
                // editText_title, editText_createTime은 outer 메소드의 final 지역 변수이다.
                // outer 메소드의 final 지역 변수는 inner 메소드에서 사용할 수 있다.
                CharSequence s1 = editText_title.getText();
                CharSequence s2 = editText_createDate.getText();

                // item은 outer 클래스의 final 지역 변수이다.
                // 데이터 항목 객체에 입력된 데이터 채우기
                item.setTitle(s1.toString());
                item.setCreateTimeFormatted(s2.toString());
                // 수정된 내용 DB에 저장
                activity.firebaseDbService.updateInServer(activity.selectedIndex);
            } // onClick 메소드의 끝
        });
        // 대화상자에 '저장' 버튼을 추가하는 코드의 끝

        builder.setNegativeButton("취소", null); // 대화상자에 '취소' 버튼을 추가하기
        AlertDialog dialog = builder.create(); // 대화상자 객체 생성하기
        return dialog; // 생성된 대화상자 객체 리턴
    }
}


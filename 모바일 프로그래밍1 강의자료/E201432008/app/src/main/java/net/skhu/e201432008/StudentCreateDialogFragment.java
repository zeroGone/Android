package net.skhu.e201432008;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class StudentCreateDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity activity = (MainActivity)getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("학생 등록");
        final View rootView = activity.getLayoutInflater().inflate(R.layout.item_create, null);

        final EditText editText_number = rootView.findViewById(R.id.create_editText1);
        final EditText editText_name = rootView.findViewById(R.id.create_editText2);

        builder.setView(rootView);
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String temp = editText_number.getText().toString();
                if(temp.equals("")) Toast.makeText(activity, "학번 꼭 입력해주세요", Toast.LENGTH_LONG).show();
                else{
                    int number = Integer.parseInt(temp);
                    String name = editText_name.getText().toString();
                    activity.studentList.add(new Student(name, number));
                    activity.myRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("취소", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}

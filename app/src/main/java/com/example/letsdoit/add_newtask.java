package com.example.letsdoit;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.letsdoit.utils.DbNotes;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class add_newtask extends BottomSheetDialogFragment {
    public static final String TAG="ADDNEWTASK";
    EditText editText;
    Button save;

    DbNotes db;
    public static add_newtask add(){
        return new add_newtask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.newtask,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText=view.findViewById(R.id.editTextText);
        save=view.findViewById(R.id.button);

        db=new DbNotes(getActivity());

        Boolean isupdated=false;

        Bundle bundle=getArguments();
        if(bundle!=null){
            isupdated=true;
            String task=bundle.getString("task");
            editText.setText(task);

            if(task.length()>0){
                save.setEnabled(false);
            }
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    save.setEnabled(false);
                    save.setBackgroundColor(Color.GRAY);
                }else {
                    save.setEnabled(true);
                    save.setBackgroundColor(Color.BLUE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Boolean finalIsupdated = isupdated;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task=editText.getText().toString();

                if(finalIsupdated){
                    db.update(bundle.getInt("id"),task) ;
                }else {
                    Modeal tas=new Modeal();
                    tas.setTask(task);
                    tas.setDone(0);
                    db.insert(tas);

                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof onclose){
            ((onclose)activity).onclose(dialog);
        }
    }
}

package com.vadivelansr.android.habittracker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vadivelansr.android.habittracker.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vadivelansr on 6/11/2016.
 */
public class NewHabitDialogFragment extends AppCompatDialogFragment {
    @Bind(R.id.input_habit)
    TextInputEditText inputHabit;
    @Bind(R.id.input_layout_habit)
    TextInputLayout layoutHabit;
    private OnAddHabitListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogRootView = inflater.inflate(R.layout.fragment_dialog_new_habit, null);
        ButterKnife.bind(this, dialogRootView);
        return dialogRootView;
    }

    @OnClick(R.id.button_add)
    public void onClickAdd(View view) {
        if(!TextUtils.isEmpty(inputHabit.getText().toString().trim()) && mListener != null){
            mListener.onAddHabit(inputHabit.getText().toString().trim());

        }else{
            layoutHabit.setError(getString(R.string.error_enter_habit));
            return;
        }
    }

    @OnClick(R.id.button_cancel)
    public void onClickCancel() {
        getDialog().dismiss();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddHabitListener) {
            mListener = (OnAddHabitListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnAddHabitListener{
        void onAddHabit(String habitName);
    }
}

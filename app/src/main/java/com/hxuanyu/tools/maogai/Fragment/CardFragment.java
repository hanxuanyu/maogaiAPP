package com.hxuanyu.tools.maogai.Fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hxuanyu.tools.maogai.Bean.QuestionsList;
import com.hxuanyu.tools.maogai.R;
import com.hxuanyu.tools.maogai.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class CardFragment extends Fragment {
    private CardView mCardView;
    private TextView questionStem;
    private TextView questionDifficult;
    private TextView questionContent;
    private Button showAnswerBtn;
    private ListView answersList;
    private QuestionsList.Question question;
    private ArrayList<String> answers;
    private boolean isShowAnsers=true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adapter, container, false);
        Bundle bundle = getArguments();
        question = (QuestionsList.Question) bundle.getSerializable("question");
        mCardView = (CardView) view.findViewById(R.id.cardView);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);

        answers = initData();
        answersList = view.findViewById(R.id.answers_list);
        answersList.setAdapter(new CustomAdapter());
        answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checkedItemPositions = answersList.getCheckedItemPositions();
                boolean isChecked = checkedItemPositions.get(position);
                //MyToast.show(getActivity(),"item " + position + " isChecked=" + isChecked);
            }
        });

        if(question.getType().equals("多选题")||question.getType().equals("填空题")){
            answersList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }else{
            answersList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
        showAnswerBtn = view.findViewById(R.id.show_answer);
        questionStem = view.findViewById(R.id.question_stem);
        questionStem.setText(question.getStem());
        questionContent = view.findViewById(R.id.question_content);
        questionContent.setText(question.getContent()+"·"+question.getType());
        questionDifficult = view.findViewById(R.id.question_difficult);
        questionDifficult.setText(setDiff(question.getDifficult()));

        showAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchButton();
            }
        });
        return view;
    }

    public void switchButton(){
        if(isShowAnsers){
            showAnswerBtn.setText(question.getCorrectAnswer());
        }else{
            showAnswerBtn.setText("查看答案");
        }
        isShowAnsers = !isShowAnsers;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Bundle bundle = getArguments();
//        question = (QuestionsList.Question) bundle.getSerializable("question");
    }

    public static CardFragment newInstance(QuestionsList.Question question) {
        CardFragment f = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("question",question);
        f.setArguments(bundle);
        return f;
    }

    public CardView getCardView() {
        return mCardView;
    }

    private ArrayList<String> initData() {
        answers = new ArrayList<>();
        String ss[] = question.getAnswers().split("/",question.getAnswerCount());
        answers.add(question.getStem());
        Collections.addAll(answers, ss);
        return answers;
    }

    private String setDiff(String deff){
        String  result = "☆☆☆";
        switch (deff){
            case "易":
                result = "★☆☆";
                break;
            case "中":
                result = "★★☆";
                break;
            case "难":
                result = "★★★";
                break;
        }

        return result;
    }



    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return answers.size();
        }

        @Override
        public String getItem(int position) {
            return answers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return answers.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {

            if(position==0){
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.question_stem_layout, container, false);
                }
                ((TextView) convertView.findViewById(R.id.tv_content))
                        .setText(question.getStem());
            }
            else{
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_lv_multiple_choice, container, false);
                }
                ((TextView) convertView.findViewById(R.id.tv_content))
                        .setText(((char)(65+position-1))+"、  "+getItem(position));
            }
            return convertView;
        }
    }
}

package com.fightbackfoods.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fightbackfoods.R;
import com.fightbackfoods.model.Category;
import com.fightbackfoods.model.HealthAspectCategory;
import com.fightbackfoods.model.Journal;
import com.fightbackfoods.model.JournalSuggestion;
import com.fightbackfoods.model.SubCategory;
import com.fightbackfoods.utils.MyGlide;
import com.fightbackfoods.utils.Validate;

import org.apache.commons.text.WordUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.fightbackfoods.model.Journal.PHYSICAL;
import static com.fightbackfoods.model.Journal.EMOTIONAL;

public class ImFeelingDialogFragment extends DialogFragment {
    private static final String TAG = ImFeelingDialogFragment.class.getSimpleName();

    public static final String KEY_MENTAL_RATING = "mental_rating";
    public static final String KEY_PHYSICAL_RATING = "physical_rating";
    public static final String KEY_HAS_NEXT = "has_next";

    public static final String KEY_EMOTIONAL = "emotional";
    public static final String KEY_PHYSICAL = "physical";
    public static final String KEY_CATEGORY = "category";



    Unbinder unbinder;

    @BindView(R.id.iv_close)
    ImageView ivClose;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_iam)
    TextView tvIam;
    @BindView(R.id.iv_emotion)
    ImageView ivEmotion;
    @BindView(R.id.iv_physical)
    ImageView ivPhysical;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.iv_next)
    ImageView ivNext;


    @BindView(R.id.tv_emotion_content)
    TextView tvEmotion;
    @BindView(R.id.tv_physical_content)
    TextView tvPhysical;


    public ImFeelingDialogFragment() {
    }

    public static ImFeelingDialogFragment newInstance(String title) {
        ImFeelingDialogFragment frag = new ImFeelingDialogFragment();
        Bundle args = new Bundle();
        //args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public static ImFeelingDialogFragment newInstance(JournalSuggestion physical) {
        ImFeelingDialogFragment frag = new ImFeelingDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PHYSICAL, physical);
        args.putInt(KEY_CATEGORY, PHYSICAL);
        frag.setArguments(args);
        return frag;
    }
    public static ImFeelingDialogFragment newInstance(List<JournalSuggestion> emotional, List<JournalSuggestion> physical) {
        ImFeelingDialogFragment frag = new ImFeelingDialogFragment();
        Bundle args = new Bundle();
        boolean hasNext = false;
        if(!emotional.isEmpty()) {
            args.putSerializable(KEY_EMOTIONAL, emotional.get(0));
            args.putInt(KEY_CATEGORY, EMOTIONAL);

        }
        if(!physical.isEmpty()) {
            args.putSerializable(KEY_PHYSICAL, physical.get(0));
            args.putInt(KEY_CATEGORY, PHYSICAL);
        }
        args.putBoolean(KEY_HAS_NEXT, (!emotional.isEmpty() && physical.isEmpty()));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.content_journal_2, container);

        unbinder = ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Bundle args = getArguments();
        if(args!=null){
            try {
                boolean hasNext = args.getBoolean(KEY_HAS_NEXT, false);
                int category = args.getInt(KEY_CATEGORY, EMOTIONAL);
                JournalSuggestion suggestion = (JournalSuggestion) args.getSerializable(KEY_EMOTIONAL);
                if(category==PHYSICAL) {
                    tvIam.setText(R.string.physically_i_am);
                    suggestion = (JournalSuggestion) args.getSerializable(KEY_PHYSICAL);
                }

                tvTitle.setText(WordUtils.capitalizeFully(suggestion.getTitle()));
                ivEmotion.setImageResource(Journal.ratingToRes(category));
                tvEmotion.setText(suggestion.getContent());
                MyGlide.load(getContext(), suggestion.getImageUrl(),ivBanner);
                if(hasNext){
                    ivNext.setVisibility(hasNext? View.VISIBLE:View.GONE);

                    ivNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                ImFeelingDialogFragment fragment = ImFeelingDialogFragment.newInstance(
                                        (JournalSuggestion) getArguments().getSerializable(KEY_PHYSICAL));
                                fragment.show(getFragmentManager(), "fragment_im_feeling");
                                dismiss();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        }catch (NullPointerException e){

        }
    }

}

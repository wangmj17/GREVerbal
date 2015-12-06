package com.example.greverbal;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;


public class DiscreteQuestionsActivity extends Activity {
	
	private GestureDetector mDetector;
	private static DiscreteQuestionsActivity discreteQuestionsActivity;
	private ScrollView mainScrollView;
	private ScrollView textScrollView;
	private QuestionTextScrollView questionTextScrollView;
	private QuestionTabBar questionTabBar;
	private QuestionBottomBar questionBottomBar;
	private TextView questionText;
	private AnswerView answerView;
	private RelativeLayout mainScrollViewLayout;
	private Button showExplanationButton;
	private Button checkAnswerButton;
	private Button nextButton;
	private Button lastButton;
	private XmlPullParser parser;
	private AttributeSet attrs;
	private WordBookHandler wordBookHandler;
	private int numOfOptions;
	private int questionType;
	private int currentIndex = 0;
	private int questionCount;
	private int screenHeight;
	private int lastID;
	private String[] options;
	private String[] explanations;
	private DiscreteExercise exercise;
	private DiscreteQuestion question;
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        discreteQuestionsActivity = this;
        
        mainScrollView = (ScrollView) this.findViewById(R.id.MainScrollView);
        textScrollView = (ScrollView) this.findViewById(R.id.TextScrollView);
        questionTextScrollView = (QuestionTextScrollView) this.findViewById(R.id.QuestionTextScrollView);
        questionTabBar = (QuestionTabBar) this.findViewById(R.id.QuestionTabBar);
        questionBottomBar = (QuestionBottomBar) this.findViewById(R.id.QuestionBottomBar);
        questionText = (TextView) this.findViewById(R.id.QuestionText);
        answerView = (AnswerView) this.findViewById(R.id.AnswerView);
        showExplanationButton = questionBottomBar.getShowExplanationButton();
        checkAnswerButton = questionBottomBar.getCheckAnswerButton();
        lastButton = questionBottomBar.getLastButton();
        nextButton = questionBottomBar.getNextButton();
        mainScrollViewLayout = (RelativeLayout) this.findViewById(R.id.MainScrollViewLayout);
    	parser = this.getResources().getXml(R.layout.optionbuttonview);
    	attrs = Xml.asAttributeSet(parser);
    	
    	WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        
        AssetManager assetManager = this.getAssets();
		
		QuestionGetter questionGetter = new QuestionGetter(assetManager);
		exercise = questionGetter.getExercise(0);
		questionCount = exercise.questionCount;
		
		wordBookHandler = new WordBookHandler(this);
		
        init();
        
        setQuestion();
    }
    
	private void init(){
		
		if (currentIndex == 0){
			lastButton.setEnabled(false);
			lastButton.setAlpha(0.5f);
		}
		if (currentIndex == questionCount - 1){
			nextButton.setEnabled(false);
			nextButton.setAlpha(0.5f);
		}
		
        mDetector = new GestureDetector(this, new WholeTextGestureListener());  
        questionTextScrollView.setLongClickable(true);  
        questionTextScrollView.setOnTouchListener(new OnTouchListener() {  
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				mDetector.onTouchEvent(arg1);
				return true;
			}  
        }); 
        
        ViewTreeObserver vto = questionBottomBar.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
        	@Override
        	public boolean onPreDraw() {
        		int margin = questionBottomBar.getHeight();
        		questionBottomBar.getViewTreeObserver().removeOnPreDrawListener(this);
                questionBottomBar.setButtonWidth(margin);
        		return true;
        	}
        });
        
        mainScrollView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				textScrollView.getParent().requestDisallowInterceptTouchEvent(false);
				return false;
			}
		});
        
        textScrollView.setOnTouchListener(new View.OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				arg0.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
        	
        });
        
        lastButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				nextButton.setEnabled(true);
				nextButton.setAlpha(1.0f);
				clearScene();
				currentIndex --;
				setQuestion();
				if (currentIndex == 0){
					lastButton.setEnabled(false);
					lastButton.setAlpha(0.5f);
				}
			}
		});
        
        nextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lastButton.setEnabled(true);
				lastButton.setAlpha(1.0f);
				clearScene();
				currentIndex ++;
				setQuestion();
				if (currentIndex == questionCount - 1){
					nextButton.setEnabled(false);
					nextButton.setAlpha(0.5f);
				}
			}
		});
        
        showExplanationButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < numOfOptions; i++){
					OptionButtonView optionButtonView = (OptionButtonView) discreteQuestionsActivity.findViewById(250 + i);
					optionButtonView.showOrHideExplanation();
				}
			}
		});
        
        
        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showAnswer();
				for (int i = 0; i < numOfOptions; i++){
					OptionButtonView optionButtonView = (OptionButtonView) discreteQuestionsActivity.findViewById(250 + i);
					optionButtonView.showExplanation();
				}
				showExplanationButton.setClickable(false);
				checkAnswerButton.setClickable(false);
			}
		});
	}
	
	private void setQuestion(){
		
		answerView.setVisibility(View.GONE);
		
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) questionTextScrollView.getLayoutParams();
    	lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
    	questionTextScrollView.setLayoutParams(lp);
    	
        ViewTreeObserver vto = questionText.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
        	@Override
        	public boolean onPreDraw() {
        		int height = questionText.getHeight();
        		questionText.getViewTreeObserver().removeOnPreDrawListener(this);
                setQuestionTextScrollViewHeight(height);
        		return true;
        	}
        });
        
		question = exercise.discreteQuestions[currentIndex];
		
        questionType =  question.type;
        numOfOptions = question.numOfOptions;
        questionTextScrollView.setText(question.text);
        options = question.options;
        explanations = question.explanations;
		
    	setOptions();
	}
	
	private void clearScene(){
		for (int i = 1; i <= lastID - 250; i++){
			View view = (View) discreteQuestionsActivity.findViewById(250 + i);
			mainScrollViewLayout.removeView(view);
		}
	}
	
	private void showAnswer(){
		
    	answerView.setVisibility(View.VISIBLE);
    	RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	lp.addRule(RelativeLayout.BELOW, 250 + numOfOptions - 1);
    	lp.setMargins(dip2px(this,5), dip2px(this,10), dip2px(this,5), dip2px(this,10));
    	answerView.setLayoutParams(lp);
		answerView.setYourAnswerContent("");
		answerView.setRightAnswerContent(question.answer);
		
	}
	
    private void setOptions(){
    	
    	lastID = 250;
    	for (int i = 0; i < numOfOptions; i ++){
    		
    		if(questionType >= 2 && i % 3 == 0){
    			String s = "Blank(";
    			for (int j = 0; j <= i / 3; j++){
    				s = s + "i";
    			}
    			s = s + ")";
    			TextView label = new TextView(this);
    			label.setText(s);
    			RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    			if (lastID > 250){
            		lp.addRule(RelativeLayout.BELOW, lastID);
        		}
        		else{
        			lp.addRule(RelativeLayout.BELOW, R.id.QuestionTextScrollView);
        		}
    			label.setId(lastID + 1);
    			lastID = lastID + 1;
    			lp.setMargins(dip2px(this,10), dip2px(this,10), dip2px(this,10), dip2px(this,5));
        		mainScrollViewLayout.addView(label,lp);
    		}
    		OptionButtonView optionButtonView= new OptionButtonView(this,attrs,wordBookHandler);
    		optionButtonView.setOptionText(options[i]);
    		optionButtonView.setOptionExplanation(explanations[i]);
    		optionButtonView.setTextSize(14);
    		optionButtonView.setOptionImage(questionType);
    		optionButtonView.setAddtoWordBookImage(this);
    		optionButtonView.setOnClickListener(new View.OnClickListener() {
    	         public void onClick(View v) {
    	        	 OptionButtonView optionButtonView = (OptionButtonView)v;
    	        	 optionButtonView.changeSelectedStatus();
    	         }
    	     });
    		RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    		if (lastID > 250){
        		lp.addRule(RelativeLayout.BELOW, lastID);
    		}
    		else{
    			lp.addRule(RelativeLayout.BELOW, R.id.QuestionTextScrollView);
    		}
    		optionButtonView.setId(lastID + 1);
    		lastID = lastID + 1;
    		lp.setMargins(dip2px(this,10), dip2px(this,10), dip2px(this,10), dip2px(this,5));
    		mainScrollViewLayout.addView(optionButtonView,lp);
    	}

    }
    
    public static void showWholeText(){
    	TextView questionText = (TextView) discreteQuestionsActivity.findViewById(R.id.QuestionText);
    	Intent intent = new Intent(discreteQuestionsActivity,WholeTextActivity.class);
    	intent.putExtra("text",questionText.getText());
    	discreteQuestionsActivity.startActivityForResult(intent, 0);
    }
    
    public void setQuestionTextScrollViewHeight(int height){
    	height = height + dip2px(this, 60);
    	if (height > screenHeight/2){
    		height = screenHeight/2;
    	}
    	QuestionTextScrollView questionTextScrollView = (QuestionTextScrollView) this.findViewById(R.id.QuestionTextScrollView);
    	RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) questionTextScrollView.getLayoutParams();
    	lp.height = height;
    	questionTextScrollView.setLayoutParams(lp);
    }
    
    public int dip2px(Context context, float dipValue){
    	float m=context.getResources().getDisplayMetrics().density ;
    	return (int)(dipValue * m + 0.5f) ;
    }

    public int px2dip(Context context, float pxValue){
       float m=context.getResources().getDisplayMetrics().density ;
       return (int)(pxValue / m + 0.5f) ;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


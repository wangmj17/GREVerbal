package com.example.greverbal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CategoryActivity extends Activity {

	private CategoryActivity categoryActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_category);
<<<<<<< HEAD
		categoryActivity = this;
		
		QuestionTabBar categoryTabBar = (QuestionTabBar) findViewById(R.id.CategoryTabBar);
		categoryTabBar.setTextViewText("题型训练");
		Button backButton = categoryTabBar.getBackButton();
		final Intent intent = getIntent();
    	backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				categoryActivity.setResult(RESULT_OK, intent);
				categoryActivity.finish();
			}
		});
    	
=======



	View bcat11 = findViewById(R.id.Category11);//�ҵ���Ҫ��͸��������layout ��id
        bcat11.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��
        View bcat12 = findViewById(R.id.Category12);//�ҵ���Ҫ��͸��������layout ��id
        bcat12.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��
        View bcat13 = findViewById(R.id.Category13);//�ҵ���Ҫ��͸��������layout ��id
        bcat13.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��
        View bcat14 = findViewById(R.id.Category14);//�ҵ���Ҫ��͸��������layout ��id
        bcat14.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��
        View bcat15 = findViewById(R.id.Category15);//�ҵ���Ҫ��͸��������layout ��id
        bcat15.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��
        View bcat16 = findViewById(R.id.Category16);//�ҵ���Ҫ��͸��������layout ��id
        bcat16.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��
        View bcat17 = findViewById(R.id.Category17);//�ҵ���Ҫ��͸��������layout ��id
        bcat17.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��
        View bcat18 = findViewById(R.id.Category18);//�ҵ���Ҫ��͸��������layout ��id
        bcat18.getBackground().setAlpha(75);//0~255͸����ֵ ��0Ϊ��ȫ͸����255Ϊ��͸��




>>>>>>> origin/master
		findViewById(R.id.Category11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, ReadingQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 0);
                startActivityForResult(intent, 0);
            }
        });
        findViewById(R.id.Category12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ReadingQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 1);
                startActivityForResult(intent, 0);
            }
        });
        findViewById(R.id.Category13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ReadingQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 2);
                startActivityForResult(intent, 0);
            }
        });
        findViewById(R.id.Category14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ReadingQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 3);
                startActivityForResult(intent, 0);
            }
        });
        findViewById(R.id.Category15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,DiscreteQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 0);
                startActivityForResult(intent, 0);
            }
        });
        findViewById(R.id.Category16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,DiscreteQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 1);
                startActivityForResult(intent, 0);
            }
        });
        findViewById(R.id.Category17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,DiscreteQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 2);
                startActivityForResult(intent, 0);
            }
        });
        findViewById(R.id.Category18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,DiscreteQuestionsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("category", 3);
                startActivityForResult(intent, 0);
            }
        });
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

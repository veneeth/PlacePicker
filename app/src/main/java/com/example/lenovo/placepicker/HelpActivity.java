package com.example.lenovo.placepicker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lenovo on 09-04-2017.
 */
public class HelpActivity extends Activity{



    static public final String ARG_TEXT_ID = "text_id";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView textView = (TextView) findViewById (R.id.help_page_intro);
        if (textView != null) {
            textView.setMovementMethod (LinkMovementMethod.getInstance());
            textView.setText (Html.fromHtml (getString (R.string.help_page_intro_html)));
        }
    }

    public void onClickHelp (View v)
    {
        int id = v.getId ();
        int textId = -1;
        switch (id) {
            case R.id.help_button1 :
                textId = R.string.topic_section1;
                break;
            case R.id.help_button2 :
                textId = R.string.topic_section2;
                break;
            case R.id.help_button3 :
                textId = R.string.topic_section3;
                break;
            case R.id.help_button4 :
                textId = R.string.topic_section4;
                break;
            default:
                break;
        }

        if (textId >= 0) startInfoActivity (textId);
        else toast ("Detailed Help for that topic is not available.", true);
    }

    public void startInfoActivity (int textId)
    {
        if (textId >= 0) {
            Intent intent = (new Intent(this, TopicActivity.class));
            intent.putExtra (ARG_TEXT_ID, textId);
            startActivity (intent);
        } else {
            toast ("No information is available for topic: " + textId, true);
        }
    }
    public void toast (String msg, boolean longLength)
    {
        Toast.makeText (getApplicationContext(), msg,
                (longLength ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
        ).show ();
    }


}

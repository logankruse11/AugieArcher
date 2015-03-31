package edu.augustana.csc490.augiearcher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create listener to launch game from the "Play" button
        Button launchGameButton=(Button) findViewById(R.id.launchGameButton);
        launchGameButton.setOnClickListener(launchGameClickHandler);

        Button settingsDialogButton=(Button) findViewById(R.id.launchInstructionButton);
        settingsDialogButton.setOnClickListener(settingsDialogClickHandler);
    }

    View.OnClickListener launchGameClickHandler=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent playGameIntent=new Intent(MainActivity.this, PlayActivity.class);
            startActivity(playGameIntent);
        }
    };
//NEED TO CREATE NEW CUSTOM LAYOUT FOR DIALOG
    Dialog.OnClickListener settingsDialogClickHandler=new Dialog.OnClickListener(){

        @Override
        public void onClick(DialogInterface instructionTestDialog, int which) {
            Dialog test=(Dialog) new instructionsTestDialog();

        }

    };


    private class instructionsTestDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_instructions)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

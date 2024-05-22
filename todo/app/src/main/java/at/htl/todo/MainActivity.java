package at.htl.todo;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import javax.inject.Inject;
import at.htl.todo.ui.layout.MainView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends ComponentActivity {

    @Inject
    MainView mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView.buildContent(this);
    }
}


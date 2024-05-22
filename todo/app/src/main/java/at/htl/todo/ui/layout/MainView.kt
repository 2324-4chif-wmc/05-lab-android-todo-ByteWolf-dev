package at.htl.todo.ui.layout

import android.text.Layout
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.Todo
import at.htl.todo.model.TodoService
import at.htl.todo.ui.theme.ToDoTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView {

    @Inject
    constructor(){}


    @Inject
    lateinit var store: ModelStore

    @Inject
    lateinit var toDoService: TodoService

    fun buildContent(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        activity.setContent {
            val viewModel = store
                .pipe
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model())
                .value

            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                Column {
                    Row {
                        LoadButton(todoService = toDoService)
                        DeleteToDos(store = store)
                    }
                    Row {
                        ToDos(model = viewModel, modifier = Modifier.padding(all = 32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ToDos(model: Model, modifier: Modifier) {
    var todos: Array<Todo> = model.todos;
    LazyColumn() {
        items(todos) {td ->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = td.id.toString())
                Text(text = td.title)
            }
        }
    }
}

@Composable
fun LoadButton(todoService: TodoService) {
    Button(onClick = {todoService.getAll()} ) {
        Text(text = "Load ToDos")
    }
}

@Composable
fun DeleteToDos(store: ModelStore) {
    Button(onClick = {store.setTodos(arrayOf())}) {
        Text(text = "Delete ToDos")
    }
}
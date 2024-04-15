package ui.screens.editScreen.addOrEditActivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.navigation.BackStackState
import ui.screens.editScreen.addOrEditActivity.editActivity.EditActivityVM
import ui.screens.editScreen.addOrEditActivity.addActivity.AddActivityVM

@Composable
fun AddOrEditActivityVM.AddOrEditScreen(modifier: Modifier, stack: BackStackState) {
    Column(
        modifier = modifier.fillMaxWidth().padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = tmpTitle,
            onValueChange = { tmpTitle = it }
        )
        Row {
            OutlinedButton(
                onClick = {
                    saveActivity()
                    stack.pop()
                },
            ) {
                Text("Save")
            }
            if (this@AddOrEditScreen is EditActivityVM) {
                OutlinedButton(
                    onClick = {
                        deleteActivity()
                        stack.pop()
                    },
                    colors = ButtonDefaults.elevatedButtonColors().copy(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            } else if (this@AddOrEditScreen is AddActivityVM) {
                OutlinedButton(
                    onClick = {
                        stack.pop()
                    }
                ) {
                    Text("Exit")
                }
            }

        }

    }
}
package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicMargin

@Composable
fun MyTextfield(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    label: String,
    singleLine: Boolean = false,
    onClearClick: () -> Unit,
    charLimit: Int? = null,
    textError: Int? = null,
    isSecret: Boolean = false
) {

    val focusManager = LocalFocusManager.current

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var keyboardOptions = KeyboardOptions()
    if (isSecret)
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    if (charLimit != null)
        KeyboardOptions.Default.copy(imeAction = ImeAction.Done)

    OutlinedTextField(
        value = value,
        onValueChange =
        if ((charLimit != null && value.length <= charLimit) || charLimit == null)
            { it -> if (value.length != charLimit || value.length > it.length) onValueChange(it) }
        else
            { _ -> },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = {
            if (charLimit != null) {
                focusManager.clearFocus()
            }
        }),
        visualTransformation = if (passwordVisible || !isSecret) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (isSecret) {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            } else if (value != "") {
                IconButton(onClick = {
                    focusManager.clearFocus()
                    onClearClick()
                }) {
                    Icon(
                        painter = rememberVectorPainter(
                            image = Icons.Default
                                .Clear
                        ),
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = singleLine,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        label = {
            Text(text = label)
        },
        supportingText = {
            Row {
                textError?.let {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = textError),
                            color = Color.Red
                        )
                    }
                }
                charLimit?.let {
                    Text(
                        text = "${value.length} / $charLimit",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = basicMargin())
    )
}

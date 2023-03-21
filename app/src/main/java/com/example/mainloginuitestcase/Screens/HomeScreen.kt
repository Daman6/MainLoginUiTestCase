package com.example.mainloginuitestcase

import android.graphics.Rect
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Pattern

@Composable
fun HomeScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val viewM = viewModel<MainViewModel>()
        TextFieldUi(viewM, navController)
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TextFieldUi(viewModel: MainViewModel, navController: NavController) {
    val textFieldValue = viewModel.textFieldText.collectAsState()
    val hasFocus = viewModel.hasFocus.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()
    val context = LocalContext.current
    val isKeyboardOpen by keyboardAsState() // Keyboard.Opened or Keyboard.Closed

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Log.e("nfjrnf", isKeyboardOpen.toString())

        TextField(
            value = textFieldValue.value,
            onValueChange = {
                viewModel.screenChanged(it)
            },
            modifier = Modifier
                .testTag("LoginTextField")
                .padding(10.dp)
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.hasFocus) {
                        viewModel.updateFocus(true)
                    } else {
                        Log.e("jdbjsbjb", "NotFocus")
                    }
                }
                .testTag("text_field"),
            label = { Text(text = "Enter your email or phone number") },
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
//            isError = !isValidPhoneNumber(phoneNumber),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
//                    checkValidNumber(phoneNumber, context)
                }
            )

        )


        val result = viewModel.getShowEmailOrMobile()
        val result1 = viewModel.isValidEmail(textFieldValue.value)

        val result2 = viewModel.isValidPhoneNumber2(textFieldValue.value)

        val loginCheckBoxValue = viewModel.loginCheckBox.collectAsState()

        when (result) {
            is ShowEmailOrMobile.EmailOptions -> {
                Checkbox(
                    checked = loginCheckBoxValue.value,
                    modifier = Modifier
                        .testTag("EmailCheckBox"),
                    onCheckedChange = {
                        viewModel.update_loginCheckBox(it)
                    }
                )

                Button(
                    onClick = {
                        if (result1) {
                            navController.navigate(Screen.Main.route)
                        } else {
                            viewModel.showToast("invalidEmail", context)
                        }
                    },
                    enabled = result1,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .testTag("EmailVerificationBtn")
                ) {
                    Text("Get Verification Code")
                }
            }
            is ShowEmailOrMobile.MobileOptions -> {
                Button(
                    onClick = {
                        if (result2) {
                            navController.navigate(Screen.Main.route)
                        } else {
                            viewModel.showToast("invalidNumber", context)
                        }
                    },
                    enabled = result2,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .testTag("MobileVerificationBtn")
                ) {
                    Text("Get Verification Code")
                }
            }
            is ShowEmailOrMobile.DefaultOptions -> {
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.updateFocus(false)
                        val result = viewModel.isEmpty(textFieldValue.value)
                        when (result) {
                            is SuccessOrError.Succes -> {
                            }
                            is SuccessOrError.Error -> {
                                Toast.makeText(
                                    context,
                                    "Please Enter Email or Phone Number",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .testTag("VerifyBtn")
                ) {
                    Text("Verify")
                }
            }
            is ShowEmailOrMobile.NoOptions -> {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Row")
                ) {
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.updateFocus(false)
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .bringIntoViewRequester(bringIntoViewRequester)
                    ) {
                        Text("Apple")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .bringIntoViewRequester(bringIntoViewRequester)
                    ) {
                        Text("Google")
                    }
                }
            }
        }

    }
}

enum class Keyboard {
    Opened, Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}


fun getAge(year: Int, month: Int, day: Int): Int {
    //calculating age from dob
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()
    dob[year, month] = day
    var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
    return age
}

fun getValidYear(year: String): Boolean {
    val ageRegex = Regex("(18|19|20)[0-9][0-9]")
    return (year.toString()).matches(ageRegex)
}

fun getValidDay(dayOfMonth: String): Boolean {
    val ageRegex = Regex("0?[1-9]|[12][0-9]|3[01]")
    return (dayOfMonth.toString()).matches(ageRegex)
}



fun getValidMonth(month: String): Boolean {
    val ageRegex = Regex("0?[1-9]|1[012]")
    return (month.toString()).matches(ageRegex)
}

fun isValidMobile(phone: String?): Boolean {
    return if(!phone.isNullOrEmpty()){

        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            phone.length in 5..15
        } else false
    } else {
        false
    }
}

//new function which accepts both india and usa number and aplhabets not allowed in this one.
fun isValid_USA_And_India_PhoneNumber(input: String): Boolean {
    val usa_regex = Regex("^\\([4-6]{1}[0-9]{2}\\)\\s?[0-9]{3}-[0-9]{4}$")
    val india_regex = Regex("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$")
    return usa_regex.matches(input) || india_regex.matches(input) && input.length in 5..15
}


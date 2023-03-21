package com.example.mainloginuitestcase

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mainloginuitestcase.viewModels.MainScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController,
) {
    WholeUi(navController = navController, viewModel = MainScreenViewModel())
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun WholeUi(navController: NavController, viewModel: MainScreenViewModel) {

    var nameText = viewModel.nameText.collectAsState()
    var emailText = viewModel.emailText.collectAsState()
    var checkBoxValue = viewModel.isChecked.collectAsState()
    var switchCheckBoxValue = viewModel.isSwitchChecked.collectAsState()
    var context = LocalContext.current
    val bringIntoViewRequester = BringIntoViewRequester()

    var scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart,
    ) {
        Column {

            Text(
                text = "Main",
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier
                    .testTag("MainText")
                    .clickable {
                        navController.navigate(Screen.Home.route)
                    }
            )
            TextField(
                value = nameText.value,
                onValueChange = {
                    viewModel.updateNameTextValue(it)
                },
                modifier = Modifier
                    .testTag("NameTextField")
                    .padding(10.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Name") },
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                    }
                )
            )
            TextField(
                value = emailText.value,
                onValueChange = {
                    viewModel.updateEmailTextValue(it)
                },
                modifier = Modifier
                    .testTag("EmailTextField")
                    .padding(10.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Email Address") },
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                    }
                )
            )
            Checkbox(
                checked = checkBoxValue.value,
                modifier = Modifier
                    .testTag("EmailCheckBox"),
                onCheckedChange = {
                    viewModel.updateCheckBoxValue(it)
                }
            )
            Switch(
                checked = switchCheckBoxValue.value,
                modifier = Modifier
                    .testTag("EmailSwitchBtn"),
                onCheckedChange = {
                    viewModel.updateSwitchCheckBoxValue(it)
//                     scope.launch {
//                        if (sheetState.isCollapsed) {
//                            sheetState.expand()
//                        } else {
//                            sheetState.collapse()
//                        }
//                    }
//                    viewModel.updateSwitchCheckBoxValue(it)
                }
            )
            Button(
                onClick = {
                    if (viewModel.checkValidation()) {
                        Toast.makeText(context, "Valid", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "InValid", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .testTag("Save&ContinueBtn")
            ) {
                Text("Save & Continue")
            }
            AnimatedVisibility(visible = switchCheckBoxValue.value) {
                Column (modifier = Modifier.padding(10.dp)){
                    OtpScreen()
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun BottomSheet(navController: NavController, viewModel: MainScreenViewModel) {
    var dateText = viewModel.dateText.collectAsState()
    var monthText = viewModel.monthText.collectAsState()
    var yearText = viewModel.yearText.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val bringIntoViewRequester = BringIntoViewRequester()
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    // A surface container using the 'background' color from the theme
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = false,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding(),
                contentAlignment = Alignment.Center,
            ) {
                Column {
                    OtpScreen()
                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .testTag("ContinueBtn")
                            .bringIntoViewRequester(bringIntoViewRequester)
                    ) {
                        Text("Continue")
                    }
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .testTag("CancelBtn")
                            .bringIntoViewRequester(bringIntoViewRequester)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        },
        sheetBackgroundColor = Color.Red,
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            WholeUi(navController = navController, viewModel = MainScreenViewModel())
        }

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpScreen() {
    val viewModel = MainViewModel()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val (digit1, setDigit1) = remember {
        mutableStateOf("")
    }
    val (digit2, setDigit2) = remember {
        mutableStateOf("")
    }
    val (digit3, setDigit3) = remember {
        mutableStateOf("")
    }

    LaunchedEffect(
        key1 = digit1,
    ) {
        if (digit1.length>=2) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Right,
            )
        }
    }
    LaunchedEffect(
        key1 = digit2,
    ) {
        if (digit2.length>=2) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Right,
            )
        }
    }
    LaunchedEffect(
        key1 = digit3,
    ) {
        if (digit3.length>=2) {
            focusManager.clearFocus()
        }
    }
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            OutlinedTextField(
                value = digit1,
                onValueChange = {
                    setDigit1(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .width(64.dp)
                    .testTag("OTP1TextField"),
            )
            OutlinedTextField(
                value = digit2,
                onValueChange = {
                    setDigit2(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .width(64.dp)
                    .testTag("OTP2TextField"),
            )
            OutlinedTextField(
                value = digit3,
                onValueChange = {
                    setDigit3(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .width(64.dp)
                    .testTag("OTP3TextField"),
            )
        }
        Button(
            onClick = {
              if (digit1.isEmpty()){
                  Toast.makeText(context,"nndd",Toast.LENGTH_SHORT).show()
              }else{
                  if (digit2.isEmpty()){
                      viewModel.showToast("Fill textfield 2",context)
                  }else{
                      if (digit3.isEmpty()){
                          viewModel.showToast("Fill textfield 3",context)
                      }
                  }
              }
            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .testTag("ContinueBtn")
        ) {
            Text("Continue")
        }
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .testTag("CancelBtn")
        ) {
            Text("Cancel")
        }
    }
}
package com.example.mainloginuitestcase

import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mainloginuitestcase.ui.theme.MainLoginUiTestCaseTheme
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpdateScreenTesting {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun SetUp() {
        composeTestRule.setContent {
            val viewModel = MainViewModel()
            val context = LocalContext.current

            val navController = rememberNavController()
            MainLoginUiTestCaseTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                )
                {
                    composable(route = Screen.Home.route) {
                        TextFieldUi(viewModel = viewModel, navController = navController)
                    }
                    composable(route = Screen.Main.route) {
                        MainScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun EnterDataInTextFields() {

        composeTestRule.onNodeWithTag("NameTextField").assertExists()
        composeTestRule.onNodeWithTag("EmailTextField").assertExists()
        composeTestRule.onNodeWithTag("NameTextField").performTextInput("Damandeep Singh")
        composeTestRule.onNodeWithTag("EmailTextField").performTextInput("Singh.damandeep53@gmail.com")
        composeTestRule.onNodeWithTag("EmailCheckBox").assertExists()
        composeTestRule.onNodeWithTag("EmailSwitchBtn").assertExists()
        composeTestRule.onNodeWithTag("EmailCheckBox").performClick()
        composeTestRule.onNodeWithTag("EmailSwitchBtn").performClick()
        composeTestRule.onNodeWithTag("OTP1TextField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("OTP2TextField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("OTP3TextField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("OTP1TextField").performTextInput("11")
        composeTestRule.onNodeWithTag("OTP2TextField").performTextInput("11")
        composeTestRule.onNodeWithTag("OTP3TextField").performTextInput("11")
    }


}
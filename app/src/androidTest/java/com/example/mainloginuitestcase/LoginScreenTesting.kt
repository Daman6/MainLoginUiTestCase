package com.example.mainloginuitestcase

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import com.example.mainloginuitestcase.ui.theme.MainLoginUiTestCaseTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginScreenTesting {

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
                    startDestination = Screen.Home.route
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
    fun EnterEmailInTextField() {

        composeTestRule.onNodeWithTag("LoginTextField").assertExists()
        composeTestRule.onNodeWithTag("Row").assertExists()
        composeTestRule.onNodeWithTag("LoginTextField")
            .performTextInput("Singh.damandeep53@gmail.com")
        composeTestRule.onNodeWithTag("Row").assertDoesNotExist()
        composeTestRule.onNodeWithTag("EmailCheckBox").assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmailVerificationBtn").assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmailCheckBox").performClick()
        composeTestRule.onNodeWithTag("EmailVerificationBtn").performClick()
    }



    @Test
    fun EnterMobileNumberInTextField() {
        composeTestRule.onNodeWithTag("LoginTextField").assertExists()
        composeTestRule.onNodeWithTag("Row").assertExists()
        composeTestRule.onNodeWithTag("LoginTextField").performTextInput("995359565")
        composeTestRule.onNodeWithTag("MobileVerificationBtn").assertIsDisplayed()
        composeTestRule.onNodeWithTag("MobileVerificationBtn").assertIsNotEnabled()
        composeTestRule.onNodeWithTag("LoginTextField").performTextClearance()
        composeTestRule.onNodeWithTag("VerifyBtn").assertExists()
        composeTestRule.onNodeWithTag("LoginTextField").performTextInput("9953595653")
        composeTestRule.onNodeWithTag("MobileVerificationBtn").assertIsEnabled()
        composeTestRule.onNodeWithTag("Row").assertDoesNotExist()
        composeTestRule.onNodeWithTag("MobileVerificationBtn").performClick()
        composeTestRule.onNodeWithTag("MainText").assertIsDisplayed()
    }
}
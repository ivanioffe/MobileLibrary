package com.ioffeivan.feature.sign_in.presentation.composable

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.ioffeivan.core.designsystem.component.PrimaryButton
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.sign_in.R
import com.ioffeivan.feature.sign_in.presentation.GoogleSignInManager
import com.ioffeivan.feature.sign_in.presentation.SignInEffect
import com.ioffeivan.feature.sign_in.presentation.SignInViewModel
import kotlinx.coroutines.launch

@Composable
internal fun SignInRoute(
    onShowSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val googleSignInManager = remember { GoogleSignInManager() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val authLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val authResult =
                        Identity.getAuthorizationClient(context)
                            .getAuthorizationResultFromIntent(result.data)

                    val accessToken = authResult.accessToken
                    viewModel.saveAccessToken(accessToken)
                } catch (_: Exception) {
                    viewModel.showError(UiText.StringResource(R.string.sign_in_error_books_access))
                }
            }
        }

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = { effect ->
            when (effect) {
                is SignInEffect.ShowResolution -> {
                    authLauncher.launch(effect.intentSenderRequest)
                }

                is SignInEffect.ShowError -> {
                    onShowSnackbar(effect.message.asString(context), null)
                }
            }
        },
    )

    SignInScreen(
        onSignInClick = {
            scope.launch {
                val googleIdTokenCredential = googleSignInManager.signIn(context)
                viewModel.handleSignIn(googleIdTokenCredential)
            }
        },
        modifier = modifier,
    )
}

@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        PrimaryButton(
            onClick = onSignInClick,
        ) {
            PrimaryIcon(
                id = PrimaryIcons.Google,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.sign_in_google_button),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    PreviewContainer {
        SignInScreen(
            onSignInClick = {},
        )
    }
}

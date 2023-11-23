package cz.mendelu.pef.xmichl.bookaroo.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.NavGraphs
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.BookarooTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookarooTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root
                )
            }
        }
    }
}

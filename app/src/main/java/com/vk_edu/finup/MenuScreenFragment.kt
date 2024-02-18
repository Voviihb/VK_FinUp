package com.vk_edu.finup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//menu
class MenuScreenFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val preferencesManager = PreferencesManager(requireContext())

        return ComposeView(
            requireContext()
        ).apply {
            setContent {
                var counter = rememberSaveable {
                    mutableStateOf(0)
                }
                val option1 = remember {
                    mutableStateOf("loading...")
                }
                val option2 = remember {
                    mutableStateOf("loading...")
                }

                fun increaseToast() {
                    counter.value += 1
                    Toast.makeText(context, counter.value.toString(), Toast.LENGTH_SHORT).show()
                }
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 600.dp, height = 250.dp)
                    ) {
                        if (option1.value == "loading...") {
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .size(width = 60.dp, height = 60.dp)
                                    .padding(
                                        start = 30.dp,
                                        top = 35.dp,
                                        bottom = 15.dp,
                                        end = 180.dp
                                    )
                                    .height(10.dp),
                                color = Color(0xFFFF9816)
                            )
                        } else {
                            Text(
                                text = option1.value, modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .size(width = 100.dp, height = 40.dp)
                                    .padding(start = 30.dp, top = 20.dp)
                            )
                            Text(
                                text = option2.value, modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .size(width = 100.dp, height = 30.dp)
                                    .padding(start = 30.dp, top = 10.dp)
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                writeUnLogged(preferencesManager)
                                launchNextScreen(parentFragmentManager) { LoginScreenFragment.newInstance() }
                            },
                            border = BorderStroke(0.dp, Color.Black),
                            modifier = Modifier
                                .padding(start = 40.dp, top = 40.dp),
                        )
                        {
                            Text(
                                text = context.getString(R.string.change_account),
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                            )
                            Image(
                                painter = painterResource(id = R.drawable.out),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp, 30.dp)
                            )
                        }

                        OutlinedButton(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(height = 100.dp, width = 100.dp)
                                .padding(start = 60.dp, bottom = 10.dp, top = 30.dp, end = 60.dp),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(0.dp)
                        )
                        {
                            Text(
                                text = context.getString(R.string.acess_by_id),
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold,
                                maxLines = 1,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier
                                    .size(height = 20.dp, width = 200.dp)
                                    .padding(start = 10.dp, end = 10.dp)
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        /* TODO SettingsScreenFragment заменить на нужный в каждом случае */
                        DrawLine()
                        rowImageText(
                            context.getString(R.string.category1),
                            R.drawable.category,
                            parentFragmentManager
                        ) { SettingsScreenFragment.newInstance() }

                        DrawLine()
                        rowImageText(
                            context.getString(R.string.category2),
                            R.drawable.income,
                            parentFragmentManager
                        ) { SettingsScreenFragment.newInstance() }

                        DrawLine()
                        rowImageText(
                            context.getString(R.string.sms_journal),
                            R.drawable.textsms,
                            parentFragmentManager
                        ) { SettingsScreenFragment.newInstance() }

                        DrawLine()
                        rowImageText(
                            context.getString(R.string.scan_qr_code),
                            R.drawable.qrcode,
                            parentFragmentManager
                        ) { SettingsScreenFragment.newInstance() }

                        DrawLine()
                        rowImageText(
                            context.getString(R.string.settings),
                            R.drawable.settings,
                            parentFragmentManager
                        ) { SettingsScreenFragment.newInstance() }

                        DrawLine()
                        rowImageText(
                            context.getString(R.string.help),
                            R.drawable.help,
                            parentFragmentManager
                        ) { SettingsScreenFragment.newInstance() }

                        DrawLine()

                        /* TODO надо что то решить с навбаром. Библиотека или что?*/
                        Row() {
                            columnImageText(
                                context.getString(R.string.operations),
                                R.drawable.operations, ::increaseToast
                            )
                            columnImageText(
                                context.getString(R.string.founds),
                                R.drawable.founds, ::increaseToast
                            )
                            columnImageText(
                                context.getString(R.string.analysis),
                                R.drawable.analyses, ::increaseToast
                            )
                            columnImageText(
                                context.getString(R.string.els),
                                R.drawable.elsepic, ::increaseToast
                            )
                        }
                    }
                    getData(option1, option2)
                }
            }
        }
    }

    companion object {
        private const val KEY_COUNTER = "key_counter"

        fun newInstance(): MenuScreenFragment {
            return MenuScreenFragment()
        }
    }

    private fun getData(option1: MutableState<String>, option2: MutableState<String>) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, e -> println(e) }).launch {
            delay(2000)
            option1.value = requireContext().getString(R.string.user)
            option2.value = requireContext().getString(R.string.user_email)

        }
    }
}




package com.vk_edu.finup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val datalist = listOf(
    R.string.settings_notifications,
    R.string.account_main_currency,
    R.string.app_language,
    R.string.color_scheme,
    R.string.period_start,
    R.string.operations_sms,
    R.string.enter_pin,
    R.string.fingerprint,
    R.string.link_accounts_byid,
    R.string.change_account,
)

val extraDatalist = listOf(
    R.string.ruble,
    R.string.russian,
    R.string.system,
    R.string.first
)

val symbols = listOf(
    R.drawable.out,
    R.drawable.clever
)

val largeContainer = listOf(
    listOf(R.string.operations_export, R.string.csv_or_xlsx),
    listOf(R.string.data_drop, R.string.accounts_operations_deleted)
)

//settings
class SettingsScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(
            requireContext()
        ).apply {
            setContent {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    item {
                        OutlinedButton(
                            onClick = ::comeBack,
                            modifier = Modifier
                                .border(
                                    0.dp,
                                    Color.Transparent,
                                    RoundedCornerShape(CornerSize(0.dp))
                                )
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.arrow),
                                contentDescription = null
                            )
                        }
                    }
                    item {
                        /* TODO remove hardcode */
                        Text(
                            text = context.getString(R.string.settings),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp, top = 30.dp)
                        )
                    }
                    items(count = 5) { countvalue ->
                        Log.d("TAG", context.getString(datalist[countvalue]))
                        if (countvalue in 1..4) {
                            SettingsItem(
                                text1 = context.getString(datalist[countvalue]),
                                text2 = context.getString(extraDatalist[countvalue - 1])
                            )
                        } else {
                            SettingsItem(text1 = context.getString(datalist[countvalue]))
                        }
                    }
                    items(count = 3) { countvalue ->
                        booleanButton(context.getString(datalist[countvalue + 5]))
                    }
                    items(count = 2) { countvalue ->
                        rowTextImageLeft(
                            text = context.getString(datalist[countvalue + 8]),
                            pic = symbols[countvalue]
                        )
                    }
                    item {
                        largeContainer(
                            context.getString(R.string.operations_export),
                            context.getString(R.string.csv_or_xlsx),
                            Color.Black,
                            Color.Black,
                            Color.Cyan
                        )
                    }
                    item {
                        largeContainer(
                            context.getString(R.string.data_drop),
                            context.getString(R.string.accounts_operations_deleted),
                            Color.Red,
                            Color.Black,
                            Color(context.getColor(R.color.orange))
                        )
                    }


                }
            }
        }
    }


    fun comeBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    companion object {
        fun newInstance() = SettingsScreenFragment()
    }
}
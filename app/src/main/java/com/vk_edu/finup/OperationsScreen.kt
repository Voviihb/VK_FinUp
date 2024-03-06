package com.vk_edu.finup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider

class OperationsScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(requireActivity())[OperationsViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                val loading by viewModel.loading
                val errorMessage by viewModel.errorMessage
                val totalMoney by viewModel.totalMoney
                val history = viewModel.historyList
                val spendMoney by viewModel.spendMoney
                val receiveMoney by viewModel.receiveMoney

                MainScreen(parentFragmentManager, loading, errorMessage, totalMoney,
                    history, spendMoney, receiveMoney)
            }
        }
    }

    companion object {
        fun newInstance() =
            OperationsScreenFragment()
    }
}

@Composable
fun MainScreen(parentFragmentManager: FragmentManager, loading: Boolean, errorMessage: String?,
               totalMoney: Double, history: List<Array<String>>, spendMoney: Double,
               receiveMoney: Double) {
    Box {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.background2),
            contentDescription = stringResource(R.string.background),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val height = LocalConfiguration.current.screenHeightDp.dp
            Column(modifier = Modifier.height(height - 105.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 30.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.amount),
                        contentDescription = stringResource(R.string.amount1)
                    )
                    Text(stringResource(R.string.title1), fontSize = 6.em, color = Color.Gray)
                    Text(String.format("%.2f", totalMoney), fontSize = 8.em)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(R.string.title1),
                            fontSize = 4.5.em,
                            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.calendar),
                            contentDescription = stringResource(R.string.choose_period),
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(30.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(0.dp, 0.dp, 5.dp, 0.dp)
                        ) {
                            AmountCard(
                                name = stringResource(R.string.amount2),
                                sum = String.format("%.2f", receiveMoney),
                                parentFragmentManager
                            ) { AccountsScreenFragment.newInstance() }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            AmountCard(
                                name = stringResource(R.string.amount3),
                                sum = String.format("%.2f", spendMoney),
                                parentFragmentManager
                            ) { NewOutcomeFragment.newInstance() }
                        }
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.title3),
                            fontSize = 4.5.em,
                            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )

                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.filter),
                            contentDescription = stringResource(R.string.sort),
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(30.dp)
                        )
                    }

                    if (history.isEmpty())
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(0.dp, 50.dp, 0.dp, 0.dp)
                                .fillMaxWidth()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(60.dp))
                        }
                    else
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        ) {
                            itemsIndexed(history) { i, arr ->
                                HistoryCard(arr)
                            }
                        }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
                    .padding(0.dp, 5.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RedirectCard(
                    R.drawable.main_screen,
                    stringResource(R.string.operations),
                    parentFragmentManager
                ) { OperationsScreenFragment.newInstance() }
                RedirectCard(
                    R.drawable.bank_accounts, stringResource(R.string.second),
                    parentFragmentManager
                ) { AccountsScreenFragment.newInstance() }
                RedirectCard(
                    R.drawable.analytics, stringResource(R.string.third),
                    parentFragmentManager
                ) { OperationsScreenFragment.newInstance() }
                RedirectCard(
                    R.drawable.more, stringResource(R.string.forth),
                    parentFragmentManager
                ) { MenuScreenFragment.newInstance() }
            }
        }
    }
}

@Composable
fun AmountCard(
    name: String,
    sum: String,
    parentFragmentManager: FragmentManager,
    func: () -> Fragment
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier.height(28.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(name, fontSize = 4.em, color = Color.Gray)
            }

            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.go_to),
                contentDescription = stringResource(R.string.scroll_all),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(15.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(sum, fontSize = 4.5.em)

            Button(
                onClick = { addFragmentBackStack(parentFragmentManager, func) },
                contentPadding = PaddingValues(0.dp),
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.size(40.dp)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.plus),
                    contentDescription = stringResource(R.string.add),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(40.dp)
                )
            }

        }
    }
}

@Composable
fun HistoryCard(arr: Array<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.basket),
                        contentDescription = stringResource(R.string.icon_of_category),
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(30.dp)
                    )
                    Column(
                        modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            arr[0],
                            fontSize = 4.5.em,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                        Text(arr[1], fontSize = 3.5.em, color = Color.Gray)
                        Text(arr[2], fontSize = 3.5.em, color = Color.Gray)
                    }
                }

                Text(arr[3], fontSize = 4.5.em)
            }
        }
    }
}


@Composable
fun RedirectCard(
    resourse: Int, name: String, parentFragmentManager: FragmentManager,
    func: () -> Fragment
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier.clickable(onClick = { addFragmentBackStack(parentFragmentManager) { func() } })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(resourse),
                contentDescription = stringResource(R.string.go_to),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(60.dp)
            )
            Text(name, fontSize = 4.em)
        }
    }
}

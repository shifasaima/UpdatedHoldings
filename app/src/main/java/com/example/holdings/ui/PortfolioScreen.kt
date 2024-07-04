package com.example.holdings.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.holdings.models.Holding

@Composable
fun PortfolioScreen(viewModel: PortfolioViewModel = hiltViewModel()) {
    val portfolio by viewModel.portfolio.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        portfolio?.let {
            PortfolioListWithSummary(it.data.userHoldings)
        }
    }
}

@Composable
fun PortfolioListWithSummary(holdings: List<Holding>) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(holdings) { holding ->
                PortfolioItem(holding)
                Divider()
            }
        }
        PortfolioSummary(holdings)
    }
}

@Composable
fun PortfolioItem(holding: Holding) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = holding.symbol, style = MaterialTheme.typography.h6)
            Text(text = "Net Qty: ${holding.quantity}")
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "LTP: ₹${holding.ltp}")
            val pnl = (holding.ltp - holding.avgPrice) * holding.quantity
            Text(
                text = "P&L: ₹%.2f".format(pnl),
                color = if (pnl >= 0) Color.Green else Color.Red,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun PortfolioSummary(holdings: List<Holding>) {
    var expanded by remember { mutableStateOf(false) }

    val currentValue = holdings.sumOf { it.ltp * it.quantity }
    val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
    val totalPnL = currentValue - totalInvestment
    val todayPnL = holdings.sumOf { (it.close - it.ltp) * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Current value*")
                    Text(text = "₹%.2f".format(currentValue))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total investment*")
                    Text(text = "₹%.2f".format(totalInvestment))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Today’s Profit & Loss*")
                    Text(
                        text = "₹%.2f".format(todayPnL),
                        color = if (todayPnL >= 0) Color.Green else Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Profit & Loss*")
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
                Text(
                    text = "₹%.2f (%.2f%%)".format(totalPnL, (totalPnL / totalInvestment) * 100),
                    color = if (totalPnL >= 0) Color.Green else Color.Red
                )
            }
        }
    }
}

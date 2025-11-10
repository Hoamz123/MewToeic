package com.hoamz.toeic.ui.screen.vocabulary.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider

object StatisticsChart {
    private val RangeProvider = CartesianLayerRangeProvider.fixed(maxY = 100.0)
    @Composable
    fun _chartProgressLearnVocab(
        modelProducer: CartesianChartModelProducer,
        modifier: Modifier = Modifier,
    ) {
        val lineColor = Color.Green
        CartesianChartHost(
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
                            areaFill = LineCartesianLayer.AreaFill.single(
                                fill(
                                    ShaderProvider.verticalGradient(
                                        arrayOf(
                                            lineColor.copy(alpha = 0.6f),
                                            Color.Transparent
                                        )
                                    )
                                )
                            ),
                        )
                    ),
                    rangeProvider = RangeProvider,
                ),
            ),
            modelProducer,
            modifier = modifier,
            rememberVicoScrollState(scrollEnabled = true),
        )
    }

    @Composable
    fun ChartProgressLearnVocab(
        modifier: Modifier = Modifier,
        x : List<Int>,
        y : List<Number>
    ) {
        val modelProducer = remember { CartesianChartModelProducer() }
        LaunchedEffect(Unit) {
            modelProducer.runTransaction {
                lineSeries { series(x, y) }
            }
        }
        _chartProgressLearnVocab(modelProducer, modifier)
    }
}
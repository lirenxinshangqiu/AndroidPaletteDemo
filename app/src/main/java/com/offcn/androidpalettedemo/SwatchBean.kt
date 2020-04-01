package com.offcn.androidpalettedemo

import android.graphics.Color
import androidx.palette.graphics.Palette

/**
 * ==============================
 *  作者：lushan
 *  创建时间：2020-02-21
 *  描述:
 * ===============================
 */
class SwatchBean {
    var swatch: Palette.Swatch? = null
    var rgb: Int = 0
    var population: Int = 0
    var titleTextColor: Int = 0
    var bodyTextColor: Int = 0

    var rgbStr:String = ""
    var popularStr:String = ""
    var titleTextColorStr:String =""
    var bodyTextColorStr:String = ""

    constructor(swatch: Palette.Swatch?, type: Int) {
        this.swatch = swatch
        rgb = swatch?.rgb?:0
        bodyTextColor = swatch?.bodyTextColor?:0
        population = swatch?.population?:0
        titleTextColor = swatch?.titleTextColor?:0
        setStrByType(type)
    }

    private fun setStrByType(type: Int) {
        titleTextColorStr = "标题颜色"
        bodyTextColorStr = "内容颜色"
        when(type){
            1->{
                rgbStr = "主色调rgb"
                popularStr = "主色调"
            }
            2->{
                rgbStr = "柔和色调rgb"
                popularStr = "柔和色调"
            }
            3->{
                rgbStr = "柔和的黑色调rgb"
                popularStr = "柔和的黑色调"
            }
            4->{
                rgbStr = "柔和的亮色调rgb"
                popularStr = "柔和的亮色调"
            }
            5->{
                rgbStr = "充满活力的色调rgb"
                popularStr = "充满活力的色调"
            }
            6->{
                rgbStr = "充满活力的黑色调rgb"
                popularStr = "充满活力的黑色调"
            }
            7->{
                rgbStr = "充满活力的亮色调rgb"
                popularStr = "充满活力的亮色调"
            }
        }

    }


}
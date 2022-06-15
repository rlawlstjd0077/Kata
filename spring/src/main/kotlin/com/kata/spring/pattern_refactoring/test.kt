package com.kata.spring.pattern_refactoring


/**
 * @author Jay
 */
class test {
}

class Solution {
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        //각 interval 은 정렬이 되어 있는가 ? (없다면 start 기준으로 한번 정렬을 해주는게 좋을 듯)
        //3개 이상의 interval 이 겹치는 경우에는 어떻게 하는게 좋을지?

        //겹치는 뭉떵이를 하나의 객체로 만들어서 이전 객체를 계속 바라보면서 합쳐가면 되지 않을까?

        val sortedIntervals = intervals.sortedBy { it[0] }
        val result = sortedIntervals.fold(mutableListOf<Interval>()) { list,  interval ->
            val interval = Interval(interval[0], interval[1])

            if (list.isEmpty() || !list.last().contains(interval)) {
                list += interval
            } else {
                list.last().mergeInterval(interval)
            }

            list
        }

        println(result)
        return result.map { it.toIntArray() }.toTypedArray()
    }
}

fun main() {
    //println(Solution().merge(arrayOf(intArrayOf(1, 4), intArrayOf(4, 5))))
    println(Solution().merge(arrayOf(intArrayOf(1, 3), intArrayOf(2, 6), intArrayOf(8, 10))))
}

data class Interval(
    var start: Int,
    var end: Int
) {

    fun mergeInterval(interval: Interval) {
        start = minOf(interval.start, start)
        end = maxOf(interval.end, end)
    }

    fun contains(interval: Interval): Boolean {
        return (interval.start in start..end) || (start in interval.start..interval.end)
    }

    fun toIntArray(): IntArray {
        return intArrayOf(start, end)
    }
}
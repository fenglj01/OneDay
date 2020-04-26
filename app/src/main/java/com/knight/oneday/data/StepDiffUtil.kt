package com.knight.oneday.data

import androidx.recyclerview.widget.DiffUtil

object StepDiffUtil : DiffUtil.ItemCallback<Step>() {
    override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
        return oldItem.stepId == newItem.stepId
    }

    override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
        return oldItem.stepId == newItem.stepId &&
                oldItem.state == newItem.state &&
                oldItem.content == newItem.content &&
                oldItem.eventId == newItem.eventId &&
                oldItem.serialNumber == newItem.serialNumber
    }
}
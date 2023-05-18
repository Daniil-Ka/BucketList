package com.tms.bucketlist.ui.targets

import com.tms.bucketlist.domain.Target

interface TargetsActionListener {

    fun onTargetClick(target: Target)

    /** удалить */
    fun onTargetRemove(target: Target)
}
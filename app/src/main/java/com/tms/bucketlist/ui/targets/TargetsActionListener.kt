package com.tms.bucketlist.ui.targets

import com.tms.bucketlist.domain.Target

interface TargetsActionListener {

    fun onTargetClick(target: Target)

    /** получить уникальный номер выбранной цели */
    fun onPersonGetId(target: Target)

    /** удалить */
    fun onTargetRemove(target: Target)
}
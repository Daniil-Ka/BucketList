package com.tms.bucketlist.signalR

/**
 * Описывает методы подписки на внешние события.
 */
interface ISignalRService {
    /**
     * Добавляет ресурсы в подписку.
     * @param subscriptionsAdd Список ресурсов для подписки.
     */
    fun subscribe(subscriptionsAdd: List<ResourceSubscriptionModel>)

    /**
     * Удаляет ресурсы из подписки.
     * @param subscriptionsRemove Список ресурсов для отписки.
     */
    fun unsubscribe(subscriptionsRemove: List<ResourceSubscriptionModel>)
}

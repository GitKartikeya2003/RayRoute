package com.example.PayRouteBackend.webhooks;

import org.springframework.data.jpa.repository.JpaRepository;


public interface WebhookRepository extends JpaRepository<Webhook, String> {
}

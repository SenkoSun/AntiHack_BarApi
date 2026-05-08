# 🍸 Black Bartender API

## 📝 Description

Api for a mysterious bar where the bartender takes orders, mixes cocktails, keeps score, and remembers every customer. The bartender has his own character, secret recipes, and special rules.

This project is an exact behavioral clone of the reference bar API. It supports user registration, ordering and mixing cocktails, a ranking system, bartender mood management, tips, and order history.

The project was created for the AntiHack 2026 hackathon. The goal was to explore the reference API and implement an identical clone with all documented and hidden features.

## 🧰 Tech Stack

- Java 21
- Spring Boot 3.2
- Spring Data JPA (Hibernate)
- MySQL 8.0
- Lombok
- Maven

## ✨ Features

### 👤 Account

- POST /register – create a new user account
- POST /reset – reset account to initial state

### 🍹 Orders

- GET /menu – get cocktail menu (supports x-time header)
- POST /order – order a cocktail by name
- POST /mix – create a cocktail by ingredients

### 💰 Balance & Statistic

- GET /balance – check current balance
- POST /tip – leave a tip (affects bartender's mood)
- GET /history – view order history
- GET /profile – view user profile (rank, statistics)

## 📈 Ranking System

Rank increases when tasting unique drinks:

- Beginner
- Quest
- Regular
- Connoisseur
- Master

## 🎭 Bartender's Mood

Mood changes based on user actions:

- Hostile
- Grumpy
- Normal
- Friendly
- Generous

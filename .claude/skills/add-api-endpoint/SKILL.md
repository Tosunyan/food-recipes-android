---
name: add-api-endpoint
description: Add a new remote API endpoint end-to-end (ApiService, clients, DTO, model, mapper, repository).
---

# Add API Endpoint

Data flows: `ApiService` → `*Dto` (`core:network`) → mapper → `*Model` (`core:model`) → repository
(`core:data`) → ViewModel.

Endpoint paths are relative to the base URL provided by `BaseUrl` in `NetworkModule`. Confirm the request
parameters and the response shape (from API docs or a sample response) before writing code.

## 1. DTO (`core:network/.../network/data/`)

- `@Serializable data class XDto(...)` with property names exactly matching the JSON keys, or use `@SerialName`.
- Make optional or nullable fields nullable with defaults so missing keys don't break deserialization.
- If the response wraps a list under a root key, use `ListDto<T>`; when the key isn't already listed in its
  `@JsonNames(...)`, add it there.
- Reuse existing DTOs when the shape matches.

## 2. `ApiService` (`core:network/.../network/api/ApiService.kt`)

Add a `suspend` function with Retrofit annotations:

```kotlin
@GET("items")
suspend fun getItems(
    @Query("type") type: String
): ListDto<ItemDto>
```

`RetrofitClient` implements `ApiService` by delegation (`retrofit.create()`), so it needs no changes.

## 3. `KtorClient` (`core:network/.../client/ktor/KtorClient.kt`)

Implement the same function manually — **required**, otherwise the module won't compile:

```kotlin
override suspend fun getItems(type: String): ListDto<ItemDto> {
    return client.get("items") {
        parameter("type", type)
    }.body()
}
```

`ApiService` is resolved to either Retrofit or Ktor at runtime, so both must send identical requests (method,
path, query/body) and parse responses identically.

## 4. Domain Model (`core:model`)

Add or reuse a `*Model` data class: pure Kotlin, no Android or serialization annotations, with sensible defaults.
Implement `java.io.Serializable` if it is passed as a screen argument.

## 5. Mapper (`core:data/.../data/mappers/`)

Top-level extension functions, e.g. `fun XDto.toXModel(): XModel` and `fun ListDto<XDto>.toXModels()`.
Map nullable DTO fields to safe model defaults; keep mappers free of side effects.

## 6. Repository (`core:data/.../data/repositories/`)

Add a method to the fitting repository, or create a new one implementing `DispatcherScope`:

- Wrap calls in `withIOResultOrNull { ... }` / `withIOResultOrDefault(default) { ... }`, so failures don't crash.
- Combine with local data when needed (see `getMealsWithSavedStatus` in `RepositoryUtils.kt`).
- Register new repositories with `singleOf(::XRepository)` in `RepositoryModule`.

## 7. Verify

```bash
./gradlew :core:network:compileDebugKotlin :core:data:compileDebugKotlin
```

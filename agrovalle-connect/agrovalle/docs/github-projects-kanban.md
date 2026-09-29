# GitHub Projects — Configuración Kanban

Configurar el tablero digital con estas columnas operativas:

1. Backlog
2. Ready
3. In Progress
4. Code Review
5. Done

## Límites WIP

- **In Progress ≤ 3**
- **Code Review ≤ 2**

## Uso recomendado

Cada Issue debe estar vinculada a una HU. Cada Pull Request debe referenciar su Issue y mover la tarjeta a Code Review. La tarjeta solo pasa a Done después de que el Pull Request haya sido revisado y la CI haya terminado correctamente.

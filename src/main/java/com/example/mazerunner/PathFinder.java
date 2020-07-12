package com.example.mazerunner;

import com.example.mazerunner.models.Cell;
import com.example.mazerunner.models.enums.PathDirection;
import com.example.mazerunner.models.maze.Maze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathFinder {

    private final Maze maze;
    private final Set<Cell> visitedCells = new HashSet<>();

    public PathFinder(Maze maze) {
        this.maze = maze;
    }

    public Deque<Cell> getRoute(Cell start, Cell target) {
        Deque<Cell> horizontallyFirstPath = getRoute(start, target, true);
        Deque<Cell> verticallyFirstPath = getRoute(start, target, false);

        // choose more optimal path
        if (horizontallyFirstPath.isEmpty()) {
            return verticallyFirstPath;
        }
        if (verticallyFirstPath.isEmpty()) {
            return horizontallyFirstPath;
        }
        if (verticallyFirstPath.size() < horizontallyFirstPath.size()) {
            return verticallyFirstPath;
        } else {
            return horizontallyFirstPath;
        }
    }

    public Deque<Cell> getRoute(Cell start, Cell target, boolean horizontallyFirst) {
        Cell currentPosition = new Cell(start.getX(), start.getY());
        visitedCells.clear();
        visitedCells.add(start);

        Deque<Cell> path = new ArrayDeque<>();

        if (!canMove(currentPosition)) return path;
        path.addFirst(start);

        while ((currentPosition.getX() != target.getX() || currentPosition.getY() != target.getY())
                && !path.isEmpty()) {
            List<PathDirection> priority;
            if (horizontallyFirst) {
                priority = getHorizontalFirstDirectionsPriority(currentPosition, target);
            } else {
                priority = getVerticalFirstDirectionsPriority(currentPosition, target);
            }

            for (int i = 0; i < priority.size(); i++) {
                PathDirection direction = priority.get(i);
                if (direction == PathDirection.RIGHT) {
                    if (canMoveRight(currentPosition)) {
                        currentPosition.setX(currentPosition.getX() + 1);
                        addToPath(currentPosition, path);
                        break;
                    }
                }
                if (direction == PathDirection.DOWN) {
                    if (canMoveDown(currentPosition)) {
                        currentPosition.setY(currentPosition.getY() + 1);
                        addToPath(currentPosition, path);
                        break;
                    }
                }
                if (direction == PathDirection.LEFT) {
                    if (canMoveLeft(currentPosition)) {
                        currentPosition.setX(currentPosition.getX() - 1);
                        addToPath(currentPosition, path);
                        break;
                    }
                }
                if (direction == PathDirection.UP) {
                    if (canMoveUp(currentPosition)) {
                        currentPosition.setY(currentPosition.getY() - 1);
                        addToPath(currentPosition, path);
                        break;
                    }
                }
                if (i == priority.size() - 1) {
                    path.pop();
                    if (path.isEmpty()) break;

                    Cell cell = path.peek();
                    currentPosition = new Cell(cell.getX(), cell.getY());
                }
            }
        }
        return path;
    }

    private boolean canMove(Cell currentPosition) {
        if (canMoveUp(currentPosition)) return true;
        if (canMoveRight(currentPosition)) return true;
        if (canMoveDown(currentPosition)) return true;
        if (canMoveLeft(currentPosition)) return true;
        return false;
    }

    private boolean canMoveUp(Cell currentPosition) {
        if (!isMoveUpAvailable(currentPosition)) return false;
        if (visitedCells.contains(new Cell(currentPosition.getX(), currentPosition.getY() - 1))) return false;
        return true;
    }

    private boolean canMoveRight(Cell currentPosition) {
        if (!isMoveRightAvailable(currentPosition)) return false;
        if (visitedCells.contains(new Cell(currentPosition.getX() + 1, currentPosition.getY()))) return false;
        return true;
    }

    private boolean canMoveDown(Cell currentPosition) {
        if (!isMoveDownAvailable(currentPosition)) return false;
        if (visitedCells.contains(new Cell(currentPosition.getX(), currentPosition.getY() + 1))) return false;
        return true;
    }

    private boolean canMoveLeft(Cell currentPosition) {
        if (!isMoveLeftAvailable(currentPosition)) return false;
        if (visitedCells.contains(new Cell(currentPosition.getX() - 1, currentPosition.getY()))) return false;
        return true;
    }

    private boolean isMoveUpAvailable(Cell currentPosition) {
        if (currentPosition.getY() == 0) return false; // edge
        if (!maze.getAvailableCells()[currentPosition.getX()][currentPosition.getY() - 1]) return false; // stone
        return true;
    }

    private boolean isMoveRightAvailable(Cell currentPosition) {
        if (currentPosition.getX() == maze.getWidth() - 1) return false; // edge
        if (!maze.getAvailableCells()[currentPosition.getX() + 1][currentPosition.getY()]) return false; // stone
        return true;
    }

    private boolean isMoveDownAvailable(Cell currentPosition) {
        if (currentPosition.getY() == maze.getHeight() - 1) return false; // edge
        if (!maze.getAvailableCells()[currentPosition.getX()][currentPosition.getY() + 1]) return false; // stone
        return true;
    }

    private boolean isMoveLeftAvailable(Cell currentPosition) {
        if (currentPosition.getX() == 0) return false; // edge
        if (!maze.getAvailableCells()[currentPosition.getX() - 1][currentPosition.getY()]) return false; // stone
        return true;
    }

    private List<PathDirection> getHorizontalFirstDirectionsPriority(Cell current, Cell target) {
        List<PathDirection> results = new ArrayList<>(4);
        if (current.getX() - target.getX() > 0) {
            // need move left
            results.add(PathDirection.LEFT);
            if (current.getY() - target.getY() < 0) {
                // need move down
                results.add(PathDirection.DOWN);
                results.add(PathDirection.UP);
                results.add(PathDirection.RIGHT);
            } else {
                // need move up OR no need move vertically
                results.add(PathDirection.UP);
                results.add(PathDirection.DOWN);
                results.add(PathDirection.RIGHT);
            }
        } else if (current.getX() - target.getX() < 0) {
            // need move right
            results.add(PathDirection.RIGHT);
            if (current.getY() - target.getY() > 0) {
                // need move up
                results.add(PathDirection.UP);
                results.add(PathDirection.DOWN);
                results.add(PathDirection.LEFT);
            } else {
                // need move down OR no need move vertically
                results.add(PathDirection.DOWN);
                results.add(PathDirection.UP);
                results.add(PathDirection.LEFT);
            }
        } else {
            // no need move horizontally
            if (current.getY() - target.getY() > 0) {
                // need move up
                results.add(PathDirection.UP);
                results.add(PathDirection.RIGHT);
                results.add(PathDirection.LEFT);
                results.add(PathDirection.DOWN);
            } else {
                // need move down
                results.add(PathDirection.DOWN);
                results.add(PathDirection.LEFT);
                results.add(PathDirection.RIGHT);
                results.add(PathDirection.UP);
            }
        }
        return results;
    }

    private List<PathDirection> getVerticalFirstDirectionsPriority(Cell current, Cell target) {
        List<PathDirection> results = new ArrayList<>(4);
        if (current.getY() - target.getY() > 0) {
            // need move up
            results.add(PathDirection.UP);
            if (current.getX() - target.getX() > 0) {
                // need move left
                results.add(PathDirection.LEFT);
                results.add(PathDirection.RIGHT);
                results.add(PathDirection.DOWN);
            } else {
                // need move right OR no need move horizontally
                results.add(PathDirection.RIGHT);
                results.add(PathDirection.LEFT);
                results.add(PathDirection.DOWN);
            }
        } else if (current.getY() - target.getY() < 0) {
            // need move down
            results.add(PathDirection.DOWN);
            if (current.getX() - target.getX() < 0) {
                // need move right
                results.add(PathDirection.RIGHT);
                results.add(PathDirection.LEFT);
                results.add(PathDirection.UP);
            } else {
                // need move left OR no need move horizontally
                results.add(PathDirection.LEFT);
                results.add(PathDirection.RIGHT);
                results.add(PathDirection.UP);
            }
        } else {
            // no need move vertically
            if (current.getX() - target.getX() > 0) {
                // need move left
                results.add(PathDirection.LEFT);
                results.add(PathDirection.DOWN);
                results.add(PathDirection.UP);
                results.add(PathDirection.RIGHT);
            } else {
                // need move right
                results.add(PathDirection.RIGHT);
                results.add(PathDirection.UP);
                results.add(PathDirection.DOWN);
                results.add(PathDirection.LEFT);
            }
        }
        return results;
    }

    private void addToPath(Cell currentPosition, Deque<Cell> path) {
        Cell previousStep = path.peek();
        Cell nextStep = new Cell(currentPosition.getX(), currentPosition.getY());
        visitedCells.add(nextStep);

        // If near cells already in path except previous step
        // then remove all previous steps until this near position
        Cell up = getNearCell(currentPosition, PathDirection.UP);
        Cell right = getNearCell(currentPosition, PathDirection.RIGHT);
        Cell down = getNearCell(currentPosition, PathDirection.DOWN);
        Cell left = getNearCell(currentPosition, PathDirection.LEFT);
        if (up != null && !up.equals(previousStep) && path.contains(up)) {
            previousStep = up;
        } else if (right != null && !right.equals(previousStep) && path.contains(right)) {
            previousStep = right;
        } else if (down != null && !down.equals(previousStep) && path.contains(down)) {
            previousStep = down;
        } else if (left != null && !left.equals(previousStep) && path.contains(left)) {
            previousStep = left;
        }
        Cell cell;
        do {
            cell = path.pop();
        } while (!cell.equals(previousStep));

        path.addFirst(cell);
        path.addFirst(nextStep);
    }

    private Cell getNearCell(Cell currentPosition, PathDirection direction) {
        Cell result = null;
        switch (direction) {
            case UP: {
                if (isMoveUpAvailable(currentPosition)) {
                    result = new Cell(currentPosition.getX(), currentPosition.getY() - 1);
                }
                break;
            }
            case RIGHT: {
                if (isMoveRightAvailable(currentPosition)) {
                    result = new Cell(currentPosition.getX() + 1, currentPosition.getY());
                }
                break;
            }
            case DOWN: {
                if (isMoveDownAvailable(currentPosition)) {
                    result = new Cell(currentPosition.getX(), currentPosition.getY() + 1);
                }
                break;
            }
            case LEFT: {
                if (isMoveLeftAvailable(currentPosition)) {
                    result = new Cell(currentPosition.getX() - 1, currentPosition.getY());
                }
                break;
            }
        }
        return result;
    }
}

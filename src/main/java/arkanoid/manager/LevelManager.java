    package arkanoid.manager;

    import arkanoid.model.brick.*;

    import java.util.ArrayList;
    import java.util.List;

    /**
     * Lop quan li cac man choi (level)
     */
    public class LevelManager {
        //Dinh nghia cac hang so cho gach
        private static final int BRICK_WIDTH = 60;
        private static final int BRICK_HEIGHT = 20;
        private static final int PADDING = 5;

        //Tra ve mot danh sach cac gach List<Brick> dua tren levelNumber;
        public List<Brick> getLevel(int levelNumber, int screenWidth, int screenHeight) {
            switch (levelNumber) {
                case 1:
                    return createLevel1(screenWidth, screenHeight);
                case 2:
                    return createLevel2(screenWidth, screenHeight);
                case 3:
                    return createLevel3(screenWidth, screenHeight);
                case 4:
                    return createLevel4(screenWidth, screenHeight);
                case 5:
                    return createLevel5(screenWidth, screenHeight);
                case 6:
                    return createLevel6(screenWidth, screenHeight);
                default:
                    return createLevel1(screenWidth, screenHeight);
            }
        }

        //Ham chuyen doi mot mang String thanh danh sach gach
        // N: gach thuong, S: gach Strong, E: gach Explosive, U:gach Unbreakable, ' ': trong
        private List<Brick> createLevel(String[] layout, int screenWidth, int screenHeight) {
            List<Brick> bricks = new ArrayList<>();
            int numRows = layout.length;
            int numCols = layout[0].length();

            int offsetTop = 50; //K/c tu dinh
            //Can giua toan bo khoi gach
            int offsetLeft = (screenWidth - (numCols * (BRICK_WIDTH + PADDING))) / 2;

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    int brickX = offsetLeft + j * (BRICK_WIDTH + PADDING);
                    int brickY = offsetTop + i * (BRICK_HEIGHT + PADDING);
                    char type = layout[i].charAt(j);
                    switch (type) {
                        case 'N':
                            bricks.add(new NormalBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT));
                            break;
                        case 'E':
                            bricks.add(new ExplosiveBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT));
                            break;
                        case 'U':
                            bricks.add(new UnbreakableBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT));
                            break;
                        case 'S':
                            bricks.add(new StrongBrick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT));
                            break;
                        case ' ':
                            break;
                    }
                }
            }
            return bricks;
        }

        //Dinh nghia cac man choi
        private List<Brick> createLevel1(int w, int h) {
            String[] layout = {
                    "NNNNNNNNNN",
                    "NNNNNNNNNN",
                    "SSSSSSSSSS",
                    "SSSSSSSSSS",
                    "EEEEEEEEEE"
            };
            return createLevel(layout, w, h);
        }

        private List<Brick> createLevel2(int w, int h) {
            String[] layout = {
                    "U U U U U ",
                    " S S S S S",
                    "N N N N N ",
                    " S S S S S",
                    "U U U U U "
            };
            return createLevel(layout, w, h);
        }

        private List<Brick> createLevel3(int w, int h) {
            String[] layout = {
                    "    NN    ",
                    "   NEEN   ",
                    "  NNEENN  ",
                    " NENENENN ",
                    "NENENENENN"
            };
            return createLevel(layout, w, h);
        }

        private List<Brick> createLevel4(int w, int h) {
            String[] layout = {
                    "UUEEUUEEUU",
                    "U  S  S  U",
                    "U SSSSSS U",
                    "  E SS E  ",
                    "   NNNN   "
            };
            return createLevel(layout, w, h);
        }

        private List<Brick> createLevel5(int w, int h) {
            String[] layout = {
                    "NSNSNSNSNS", // 10
                    "SNSNSNSNSN", // 10
                    "NSESESESNS", // 10
                    "SESESESESE", // 10
                    "NSNSNSNSNS", // 10
                    "SNSNSNSNSN"  // 10
                      };
            return createLevel(layout, w, h);
        }

        private List<Brick> createLevel6(int w, int h) {
            String[] layout = {
                    "    NN    ", // 10
                    "   NSSN   ", // 10
                    "  NSSESN  ", // 10
                    "    NN    ", // 10
                    "    NN    ", // 10
                    "    NN    "  // 10
            };
            return createLevel(layout, w, h);
        }
    }

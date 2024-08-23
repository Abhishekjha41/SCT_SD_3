package others;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sudoku_Solver {

    JFrame jframe;
    JTextField inputField;
    JTextField[][] grid;
    JPanel sudoku_grid, buttonPanel, inputPanel;
    JButton solve, clear, submit;
    private int[][] board;

    public Sudoku_Solver() {
        jframe = new JFrame("Sudoku Solver");

        grid = new JTextField[9][9];
        sudoku_grid = new JPanel();
        sudoku_grid.setLayout(new GridLayout(9, 9));
        sudoku_grid.setBackground(new Color(0xC3D2D5));

        board = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col] = new JTextField();
                grid[row][col].setHorizontalAlignment(JTextField.CENTER);
                grid[row][col].setFont(new Font("SansSerif", Font.BOLD, 20));
                grid[row][col].setBackground(new Color(0xC1F7DC));
                grid[row][col].setForeground(new Color(0x824670));
                sudoku_grid.add(grid[row][col]);
            }
        }

        buttonPanel = new JPanel();
        solve = new JButton("Solve");
        clear = new JButton("Clear");

        // Add buttons to the button panel
        buttonPanel.add(solve);
        buttonPanel.add(clear);

        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // welcome_msg = new JLabel(
        // "<htmL><p style=\"font-size: medium;font-family: Georgia, 'Times New
        // Roman',Times, serif;\">Sudoku Solver</p></html>");
        // inputPanel.add(welcome_msg);

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputPanel.add(new JLabel("Input Sudoku: "));
        inputField.setPreferredSize(new Dimension(400, 30));
        inputPanel.add(inputField);

        submit = new JButton("Submit");
        submit.setBounds(200, 100, 80, 20);
        inputPanel.add(submit);

        // Adding actions to buttons
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlesubmit();
            }
        });

        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlesolve();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleclear();
            }
        });

        // Set the layout of the JFrame

        jframe.setLayout(new BorderLayout());

        // Add components to the JFrame
        jframe.add(inputPanel, BorderLayout.NORTH);
        jframe.add(sudoku_grid, BorderLayout.CENTER);
        jframe.add(buttonPanel, BorderLayout.SOUTH);

        jframe.setSize(600, 700);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void handlesubmit() {
        String input = inputField.getText();
        if (input.length() != 81) {
            JOptionPane.showMessageDialog(jframe, "Invalid input in the grid");
            handleclear();
            return;
        }
        int k = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (input.charAt(k) == '0') {
                    k++;
                    continue;
                } else {
                    grid[i][j].setText(input.charAt(k) + "");
                    k++;
                }

            }
        }

    }

    private void handleclear() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].setText("");
            }
        }
        inputField.setText("");
    }

    private void handlesolve() {
        if (makeBoard()) {
            if (solveBoard()) {
                updateGrid();
            } else {
                JOptionPane.showMessageDialog(jframe, "No solution possible for the given Puzzle");
                handleclear();
            }
        } else {
            JOptionPane.showMessageDialog(jframe, "Invalid input in the grid");
            handleclear();
        }

    }

    private boolean makeBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = grid[i][j].getText().trim();
                if (text.isEmpty()) {
                    board[i][j] = 0;
                } else {
                    try {
                        int num = Integer.parseInt(text);
                        if (num < 1 || num > 9) {
                            return false; // Invalid number
                        }
                        board[i][j] = num;
                    } catch (NumberFormatException e) {
                        return false; // Invalid input
                    }
                }
            }
        }
        return true;
    }

    public boolean isvalid(int[][] board, int c, int row, int col) {
        for (int i = 0; i <= 8; i++) {
            if (board[row][i] == c)
                return false;
            if (board[i][col] == c)
                return false;
            if (board[3 * (row / 3) + (i / 3)][3 * (col / 3) + (i % 3)] == c)
                return false;
        }
        return true;
    }

    public boolean solveBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] == 0) {
                    for (int c = 1; c <= 9; c++) {
                        if (isvalid(board, c, i, j)) {
                            board[i][j] = c;
                            if (solveBoard() == true) {
                                return true;
                            } else {
                                board[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void updateGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col].setText(board[row][col] == 0 ? "" : String.valueOf(board[row][col]));
            }
        }
    }

    public static void main(String[] args) {
        new Sudoku_Solver();
    }
}

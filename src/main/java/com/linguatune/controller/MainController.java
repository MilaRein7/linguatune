package com.linguatune.controller;

import com.linguatune.model.Playlist;
import com.linguatune.model.Song;
import com.linguatune.model.Word;
import com.linguatune.service.SongRepository;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainController {

    @FXML private TabPane mainTabPane;
    @FXML private TextField txtUserName;
    @FXML private Label lblTotalSongsValue;
    @FXML private Label lblTotalWordsValue;
    @FXML private Label lblUserNameDisplay;
    @FXML private VBox boxEditUser;
    @FXML private VBox boxViewUser;

    private static final Path PROFILE_PATH = Paths.get("profile.txt");
    private static final Path USER_DICTIONARY_PATH = Paths.get("user-dictionary.txt");

    @FXML private TableView<Song> songTable;
    @FXML private TableColumn<Song, String> colTitle;
    @FXML private TableColumn<Song, String> colArtist;
    @FXML private TableColumn<Song, String> colGenre;
    @FXML private TableColumn<Song, String> colSongLevel;
    @FXML private TextField songSearchField;
    @FXML private ComboBox<String> levelFilter;

    private final Playlist mainPlaylist = new Playlist("Основной плейлист");
    private FilteredList<Song> filteredSongs;

    @FXML private TableView<Word> wordTable;
    @FXML private TableColumn<Word, String> colWord;
    @FXML private TableColumn<Word, String> colTranslation;

    private final ObservableList<Word> wordData = FXCollections.observableArrayList();
    private final Map<String, String> baseDictionary = new HashMap<>();

    @FXML
    @SuppressWarnings("unused") // вызывается из FXML
    private void initialize() {
        loadBaseDictionary();
        initSongTable();
        initWordTable();
        loadTestSongs();
        loadUserDictionary();
        loadProfile();
        updateStatistics();

        mainPlaylist.getSongs().addListener((ListChangeListener<Song>) _ -> updateStatistics());
        wordData.addListener((ListChangeListener<Word>) _ -> {
            updateStatistics();
            saveUserDictionary();
        });
    }

    private void setProfileEditMode(boolean editing) {
        if (boxEditUser != null) {
            boxEditUser.setVisible(editing);
            boxEditUser.setManaged(editing);
        }
        if (boxViewUser != null) {
            boxViewUser.setVisible(!editing);
            boxViewUser.setManaged(!editing);
        }
    }

    private void loadProfile() {
        try {
            String name = Files.exists(PROFILE_PATH)
                    ? Files.readString(PROFILE_PATH, StandardCharsets.UTF_8).trim()
                    : "";

            if (txtUserName != null) txtUserName.setText(name);
            if (lblUserNameDisplay != null)
                lblUserNameDisplay.setText(name.isEmpty() ? "Имя не задано" : name);

            setProfileEditMode(name.isEmpty());
        } catch (IOException e) {
            System.err.println("Ошибка загрузки профиля: " + e.getMessage());
            showError("Ошибка загрузки профиля",
                    "Не удалось загрузить профиль пользователя.\n" + e.getMessage());
            setProfileEditMode(true);
        }
    }

    @FXML
    private void onSaveProfile() {
        if (txtUserName == null) return;

        String name = txtUserName.getText().trim();
        if (name.isEmpty()) {
            showWarning("Сохранение профиля",
                    "Пожалуйста, введите имя пользователя перед сохранением.");
            return;
        }

        try {
            Files.writeString(PROFILE_PATH, name, StandardCharsets.UTF_8);
            if (lblUserNameDisplay != null) lblUserNameDisplay.setText(name);
            setProfileEditMode(false);
            showInfo("Сохранение профиля", "Профиль сохранён.\nИмя пользователя: " + name);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения профиля: " + e.getMessage());
            showError("Ошибка сохранения профиля",
                    "Не удалось сохранить профиль.\n" + e.getMessage());
        }
    }

    @FXML
    private void onEditName() {
        setProfileEditMode(true);
        if (txtUserName != null) {
            txtUserName.requestFocus();
            txtUserName.selectAll();
        }
    }

    @FXML
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("LinguaTune – учим английский по песням");
        alert.setContentText("""
                Версия: 1.0
                Автор: Lyudmila Kabargina

                Описание:
                Приложение помогает учить английский язык по песням.
                Вы выбираете песню, кликаете по словам, смотрите перевод
                и добавляете их в личный словарь.
                """);
        alert.showAndWait();
    }

    private void loadBaseDictionary() {
        try (InputStream is = getClass().getResourceAsStream("/data/base-dictionary.txt")) {
            if (is == null) {
                System.err.println("Файл base-dictionary.txt не найден в resources/data");
                return;
            }
            try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                StringBuilder sb = new StringBuilder();
                int ch;
                while ((ch = reader.read()) != -1) sb.append((char) ch);
                for (String raw : sb.toString().split("\\R")) {
                    String line = raw.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        baseDictionary.put(parts[0].trim().toLowerCase(), parts[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении base-dictionary: " + e.getMessage());
        }
    }

    private void initSongTable() {
        if (songTable == null) return;
        songTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colSongLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

        filteredSongs = new FilteredList<>(mainPlaylist.getSongs(), _ -> true);
        SortedList<Song> sortedSongs = new SortedList<>(filteredSongs);
        sortedSongs.comparatorProperty().bind(songTable.comparatorProperty());
        songTable.setItems(sortedSongs);

        if (levelFilter != null) {
            levelFilter.setItems(FXCollections.observableArrayList("Все уровни", "A1", "A2", "B1", "B2", "C1"));
            levelFilter.getSelectionModel().selectFirst();
            levelFilter.valueProperty().addListener((_, _, _) -> updateSongFilter());
        }
        if (songSearchField != null) {
            songSearchField.textProperty().addListener((_, _, _) -> updateSongFilter());
        }

        songTable.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()
                        && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    openSongDetailsWindow(row.getItem());
                }
            });
            return row;
        });
    }

    private void updateSongFilter() {
        if (filteredSongs == null) return;

        String searchText = songSearchField.getText() == null
                ? ""
                : songSearchField.getText().trim().toLowerCase();
        String selectedLevel = levelFilter.getValue() == null
                ? "Все уровни"
                : levelFilter.getValue();

        filteredSongs.setPredicate(song -> {
            if (song == null) return false;
            boolean matchesText = searchText.isEmpty()
                    || song.getTitle().toLowerCase().contains(searchText)
                    || song.getArtist().toLowerCase().contains(searchText);
            boolean matchesLevel = selectedLevel.equals("Все уровни")
                    || song.getLevel().equalsIgnoreCase(selectedLevel);
            return matchesText && matchesLevel;
        });
    }

    private void loadTestSongs() {
        mainPlaylist.getSongs().setAll(SongRepository.loadDefaultSongs());
    }

    private void initWordTable() {
        if (wordTable == null) return;
        wordTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        colWord.setCellValueFactory(new PropertyValueFactory<>("text"));
        colTranslation.setCellValueFactory(new PropertyValueFactory<>("translation"));
        wordTable.setItems(wordData);
    }

    private void loadUserDictionary() {
        try {
            if (!Files.exists(USER_DICTIONARY_PATH)) return;
            List<String> lines = Files.readAllLines(USER_DICTIONARY_PATH, StandardCharsets.UTF_8);
            wordData.clear();
            for (String raw : lines) {
                String line = raw.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    wordData.add(new Word(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
            showError("Ошибка загрузки словаря",
                    "Не удалось загрузить пользовательский словарь.\n" + e.getMessage());
        }
    }

    private void saveUserDictionary() {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(USER_DICTIONARY_PATH, StandardCharsets.UTF_8)) {
            for (Word w : wordData) {
                writer.write(w.getText() + "=" + w.getTranslation());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка сохранения словаря: " + e.getMessage());
        }
    }

    @FXML
    @SuppressWarnings("unused")
    private void onSaveDictionary() {
        if (wordData.isEmpty()) {
            showInfo("Сохранение словаря",
                    "Словарь пуст. Нет слов для сохранения.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Сохранить словарь как...");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV файлы (*.csv)", "*.csv")
        );
        chooser.setInitialFileName("linguatune-dictionary.csv");

        File file = chooser.showSaveDialog(mainTabPane.getScene().getWindow());
        if (file == null) return;

        Path path = file.toPath();
        Charset win1251 = Charset.forName("windows-1251");

        try (BufferedWriter writer = Files.newBufferedWriter(path, win1251)) {
            writer.write("# english;translation");
            writer.newLine();
            for (Word w : wordData) {
                writer.write(w.getText() + ";" + w.getTranslation());
                writer.newLine();
            }
            showInfo("Сохранение словаря",
                    "Словарь сохранён в файл:\n" + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка сохранения CSV словаря: " + e.getMessage());
            showError("Ошибка сохранения",
                    "Не удалось сохранить словарь.\n" + e.getMessage());
        }
    }

    @FXML
    @SuppressWarnings("unused")
    private void onDeleteWord() {
        Word selected = wordTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Удаление слова",
                    "Пожалуйста, выберите слово в таблице перед удалением.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Удаление слова");
        confirm.setHeaderText(null);
        confirm.setContentText("Вы уверены, что хотите удалить слово: \"" +
                selected.getText() + "\"?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            wordData.remove(selected);
        }
    }

    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setTitle(title);
        a.showAndWait();
    }

    private void showWarning(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        a.setTitle(title);
        a.showAndWait();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setTitle(title);
        a.showAndWait();
    }

    private void updateStatistics() {
        lblTotalSongsValue.setText(String.valueOf(mainPlaylist.getSongs().size()));
        lblTotalWordsValue.setText(String.valueOf(wordData.size()));
    }

    private void openSongDetailsWindow(Song song) {
        if (song == null) return;

        Stage stage = new Stage();
        stage.setTitle("Песня: " + song.getTitle());

        VBox lyricsBox = new VBox(5);
        String lyrics = song.getLyrics() != null ? song.getLyrics() : "Текст песни пока не задан.";
        for (String line : lyrics.split("\\R")) {
            if (line.isBlank()) continue;
            FlowPane linePane = new FlowPane(5, 5);
            for (String rawWord : line.trim().split("\\s+")) {
                if (!rawWord.isBlank()) {
                    linePane.getChildren().add(createWordLabel(rawWord));
                }
            }
            lyricsBox.getChildren().add(linePane);
        }

        ScrollPane scroll = new ScrollPane(lyricsBox);
        scroll.setFitToWidth(true);

        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e -> stage.close());

        VBox root = new VBox(
                10,
                new Label("Название: " + song.getTitle()),
                new Label("Исполнитель: " + song.getArtist()),
                new Label("Жанр: " + song.getGenre()),
                new Label("Уровень: " + song.getLevel()),
                new Label("Кликай по слову и добавляй в словарь"),
                scroll,
                closeButton
        );
        root.setStyle("-fx-padding: 15;");

        stage.setScene(new Scene(root, 600, 450));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainTabPane.getScene().getWindow());
        stage.showAndWait();
    }

    private Label createWordLabel(String cleanWord) {
        Label label = new Label(cleanWord);
        label.setStyle(
                "-fx-border-color: #666666; " +
                        "-fx-border-radius: 4; " +
                        "-fx-padding: 2 4; " +
                        "-fx-cursor: hand;");
        label.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                handleWordClick(cleanWord);
            }
        });
        return label;
    }

    private void handleWordClick(String rawWord) {
        String englishWord = rawWord.toLowerCase().replaceAll("[^a-z]", "");
        if (englishWord.isEmpty()) return;

        String translation = baseDictionary.get(englishWord);
        if (translation == null) {
            showInfo("Слово из песни", englishWord + "\nЕще не добавили слово в базу");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Слово из песни");
        confirm.setHeaderText("Добавить слово в словарь?");
        confirm.setContentText("Слово: " + englishWord + "\nПеревод: " + translation);

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        wordData.add(new Word(englishWord, translation));
        showInfo("Слово добавлено",
                "Слово \"" + englishWord + "\" добавлено в словарь.");
    }
}
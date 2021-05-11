Feature: SearchFeature

  Scenario: Wyszukanie stanu paczki
    Given Otworzenie ekranu początkowego strony
    And Wyszukanie paczki o numerze as
      | arg0                     | arg01                    | arg02                    |
      | 680175798174312027659619 | 605080798151518110154556 | 505080798151518110154556 |
    When Sprawdzenie statusu paczek
    Then Powinienem znać statusy paczek

  Scenario: Wyszukanie stanowiska pracy
    Given Otworzenie ekranu dla stanowiska pracy
    And Wypełnienie formularza aplikacyjnego z modelu aktora
    When  Załączenie dowolnego pliku jako CV
    Then Full page screenshot z wypełnionego formularza (bez wysyłania)

  Scenario: Wyszukanie paczkomatów za pomocą API
    Given Ustanowienie GET API endpoint
    And Wysłałem zapytanie GET HTTTP
    When Otrzymałem odpowiedź odnośnie paczkomatów
    Then Upewniłem się że paczkomaty są z Krakowa




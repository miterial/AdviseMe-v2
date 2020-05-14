package com.lanagj.adviseme.recommender.nlp.lsa;

import com.lanagj.adviseme.entity.movie.Movie;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MovieDataProvider {

    public static List<Movie> provideArguments() {

        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie(1, "В центре сюжета картины, основанной на реальных событиях, окажется дело французского сотрудника спецслужб Альфреда Дрейфуса. Его обвинили в государственной измене и шпионаже в пользу Германии, после чего приговорили к разжалованию и пожизненной ссылке. На фоне антисемитских настроений конца XIX века дело стало резонансным: после того как писатель Эмиль Золя выступил в поддержку Дрейфуса, французское общество раскололось на два противоборствующих лагеря. Чтобы обелить имя бывшего офицера, его защитникам нужно найти секретную папку, в которой содержатся \"неопровержимые\" доказательства вины Дрейфуса, на которые ссылаются обвинители..."));

        movies.add(new Movie(2, "В погоне за могуществом бог Локи вступает в сговор с воинственной расой Читаури, которая обещает ему армию для захвата Земли в обмен на Тессеракт – небольшой куб, содержащий в себе огромную силу. Проникнув в секретный центр организации «Щ.И.Т.», он объявляет о своих намерениях, после чего похищает Тессеракт и берет под контроль нескольких агентов, обеспечивших ему побег. В ответ на угрозу глава «Щ.И.Т.’а» Ник Фьюри созывает сильнейших супергероев планеты, которыми оказываются Железный человек, Черная вдова, Капитан Америка, Халк и Тор. Чтобы дать противнику достойный отпор, им придется отложить все свои разногласия и научиться работать в команде, иначе человечество ждет неминуемая гибель…"));

        movies.add(new Movie(3, "В документальном фильме рассказывается о событиях, предшествующих высадке человека на Луну. Когда СССР запустил первый искусственный спутник Земли в 1957 году, тем самым закрепив за собой статус лидера «космической гонки», американцы приступили к ускоренной разработке проекта «Аполлон», желая стать первой нацией, которой удалось добраться до Луны. Однако на реализацию невероятной и трудно осуществимой цели потребовалось долгих двенадцать лет, во время которых команда инженеров, астронавтов и рядовых сотрудников НАСА столкнулась с множеством сложностей и неудач..."));

        movies.add(new Movie(4, "Энди Барбер — порядочный семьянин, занимающий должность помощника окружного прокурора в Бостоне, штат Массачусетс, в то время как его жена Лори управляет благотворительным центром для помощи больным детям. Жизнь главного героя переворачивается с ног на голову, когда его 14-летнего сына Джейкоба задерживает полиция, подозревая в убийстве своего одноклассника Бена Рифкина, чье тело было найдено в лесу со смертельным ножевым ранением. Веря, что его ребенок невиновен, мужчина готов пойти на все, чтобы добиться в суде оправдательного приговора."));

        movies.add(new Movie(5, "Богатый и успешный автор детективных романов Харлан Тромби отмечает 85-й день рождения со своей семьёй. На вечеринке он спорит практически со всеми о деньгах, бизнесе и других вещах. Утром его труп находят с перерезанным горлом в собственной спальне, а все присутствующие родственники оказываются под подозрением. За расследование берётся обаятельный частный детектив Бенуа Блан, нанятый анонимом. Вскоре сыщик понимает, что ключом к разгадке тайны смерти писателя является Марта, сиделка Харлана, страдающая от болезни, из-за которой её тошнит, когда она врёт..."));

        return movies;
    }
}

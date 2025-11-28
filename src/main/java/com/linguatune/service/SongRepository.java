package com.linguatune.service;

import com.linguatune.model.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SongRepository {
    public static ObservableList<Song> loadDefaultSongs() {
        ObservableList<Song> songs = FXCollections.observableArrayList();

        songs.add(new Song(
                "song-002",
                "Lose Yourself",
                "Eminem",
                "Rap",
                "B2",
                """
                        Look, if you had, one shot, or one opportunity
                        To seize everything you ever wanted-
                        One moment
                        Would you capture it, or just let it slip?
                     
                        His palms are sweaty, knees weak, arms are heavy
                        There's vomit on his sweater already, mom's spaghetti
                        He's nervous, but on the surface he looks calm and ready
                        To drop bombs, but he keeps on forgetting
                        What he wrote down, the whole crowd goes so loud
                        He opens his mouth, but the words won't come out
                        He's chokin, how everybody's jokin now
                        The clock's run out, time's up, over bloah
                        Snap back to reality, Oh there goes gravity
                        Oh, there goes rabbit, he choked
                        He's so mad, but he won't give up that
                        No, he won't have it , he knows his whole back city ropes
                        It don't matter, he's dope
                        He knows that, but he's broke
                        He's so stacked that he knows
                        When he goes back to his mobile home, that's when it's
                        Back to the lab again yo
                        This whole rap s**t
                        Better go capture this moment and hope it don't pass him
                     
                        You better lose yourself in the music, the moment
                        You own it, you better never let it go
                        You only get one shot, do not miss your chance to blow
                        This opportunity comes once in a lifetime yo,
                     
                        His soul's escaping, through this hole that is gaping
                        This world is mine for the taking
                        Make me king, as we move toward a, new world order
                        A normal life is borin, but superstardom's close to post mortom
                        It only grows harder, only grows hotter
                        He blows us all over these hoes is all on him
                        Coast to coast shows, he's know as the globetrotter
                        Lonely roads, God only knows
                        He's grown farther from home, he's no father
                        He goes home and barely knows his own daughter
                        But hold your nose cause here goes the cold water
                        These ho's don't want him no mo, he's cold product
                        They moved on to the next schmoe who flows
                        He nose dove and sold nada
                        So the soap opera is told and unfolds
                        I suppose it's old partner, but the beat goes on
                        Da da dum da dum da da
                     
                        You better lose yourself in the music, the moment
                        You own it, you better never let it go-o
                        You only get one shot, do not miss your chance to blow
                        This opportunity comes once in a lifetime yo
                     
                        No more games, I'ma change what you call rage
                        Tear this m*********n roof off like 2 dogs caged
                        I was playin in the beginnin, the mood all changed
                        I been chewed up and spit out and booed off stage
                        But I kept rhymin and stepwritin the next cypher
                        Best believe somebody's payin the pied piper
                        All the pain inside amplified by the fact
                        That I can't get by with my 9 to 5
                        And I can't provide the right type of life for my family
                        Cause man, these g****m food stamps don't buy diapers
                        And it's no movie, there's no Makai Pheiffer, this is my life
                        And these times are so hard and it's getting even harder
                        Tryin to feed and water my seed, plus
                        Teeter-totter caught up between bein a father and a pre-madonna
                        Baby mama drama's screamin on and
                        Too much for me to wanna
                        Stay in one spot, another day of monotony
                        Has gotten me to the point, I'm like a snail
                        I've got to formulate a plot or I end up in jail or shot
                        Success is my only m*********n option, failure's not
                        Mom, I love you, but this trailer's got to go
                        I cannot grow old in Salem's lot
                        So here I go is my shot.
                        Feet fail me not this may be the only opportunity that I got
                     
                        You better lose yourself in the music, the moment
                        You own it, you better never let it go
                        You only get one shot, do not miss your chance to blow
                        This opportunity comes once in a lifetime yo
                     
                        You can do anything you set your mind to, man
                      """
        ));

        songs.add(new Song(
                "song-003",
                "Walk on the Wild Side",
                "Lou Reed",
                "Rock",
                "B2",
                """
                        Holly came from Miami, F-L-A
                        Hitch-hiked her way across the USA
                        Plucked her eyebrows along the way
                        Shaved her legs and then he was a she
                        She says, "Hey babe, take a walk on the wild side"
                        Said, "Hey honey, take a walk on the wild side"
                        Candy came from out on the Island
                        In the backroom, she was everybody's darling
                        But she never lost her head
                        Even when she was giving head
                        She says, "Hey babe, take a walk on the wild side"
                        Said, "Hey babe, take a walk on the wild side"
                        And the colored girls go
                        Doo, do-doo, do-doo, do-do-doo
                        Doo, do-doo, do-doo, do-do-doo
                        Doo, do-doo, do-doo, do-do-doo
                        Doo, do-doo, do-doo, do-do-doo
                        Doo, do-doo, do-doo, do-do-doo
                        Doo, do-doo, do-doo, do-do-doo
                        Doo, do-doo, do-doo, do-do-doo
                        Doo, do-doo, do-doo, do-do-doo
                        Doo
                        Little Joe never once gave it away
                        Everybody had to pay and pay
                        A hustle here and a hustle there
                        New York City is the place where they said
                        "Hey babe, take a walk on the wild side"
                        I said, "Hey Joe, take a walk on the wild side"
                        Sugar Plum Fairy came and hit the streets
                        Looking for soul food and a place to eat
                        Went to the Apollo
                        You should've seen him go, go, go
                        They said, "Hey sugar, take a walk on the wild side"
                        I said, "Hey babe, take a walk on the wild side"
                        Alright, huh
                        Jackie is just speeding away
                        Thought she was James Dean for a day
                        Then I guess she had to crash
                        Valium would've helped that bash
                        She said, "Hey, babe, take a walk on the wild side"
                        I said, "Hey, honey, take a walk on the wild side"
                        And the colored girls say
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo, doo-doo, doo-doo, doo-doo-doo
                        Doo
                 """
        ));

        songs.add(new Song(
                "song-005",
                "Every You Every Me",
                "Placebo",
                "Rock",
                "B2",
                """
                        Sucker love is heaven sent
                        You pucker up, our passion's spent
                        My heart's a tart, your body's rent
                        My body's broken, yours is bent
                        Carve your name into my arm
                        Instead of stressed, I lie here charmed
                        'Cause there's nothing else to do
                        Every me and every you
                        Sucker love, a box I choose
                        No other box I choose to use
                        Another love I would abuse
                        No circumstances could excuse
                        In the shape of things to come
                        Too much poison come undone
                        'Cause there's nothing else to do
                        Every me and every you
                        Every me and every you
                        Every me
                        Sucker love is known to swing
                        Prone to cling and waste these things
                        Pucker up for heaven's sake
                        There's never been so much at stake
                        I serve my head up on a plate
                        It's only comfort, calling late
                        'Cause there's nothing else to do
                        Every me and every you
                        Every me and every you
                        Every me
                        Every me and every you
                        Every me
                        Like the naked leads the blind
                        I know I'm selfish, I'm unkind
                        Sucker love I always find
                        Someone to bruise and leave behind
                        All alone in space and time
                        There's nothing here but what here's mine
                        Something borrowed, something blue
                        Every me and every you
                        Every me and every you
                        Every me
                        Every me and every you
                        Every me
                        Every me and every you
                        Every me
                        Every me and every you
                        Every me
                        Every me and every you
                        Every me
                        Every me and every you
                        Every me
                 """
        ));

        return songs;
    }
}

const COMMENTS_BOOKMARK_ID_3 = [
  {
    commentId: 1,
    author: {
      memberId: 1,
      nickname: "뭐당가이름이",
      profileImgUrl: "www.asdjfk.com",
    },
    createdTime: "2023-09-11T14:30:00",
    reaction: {
      likeCount: 2,
    },
    content:
      '[{"id":"aff887cc-b2df-43b4-a89d-1c19e77adb08","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[{"type":"text","text":"1111","styles":{}}],"children":[]},{"id":"c8b0b593-80db-4555-bf7a-81fc0ee4200c","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[],"children":[]}]',
  },
  {
    commentId: 2,
    author: {
      memberId: 6,
      nickname: "랄랄라",
      profileImgUrl: "www.asdjfk.com",
    },
    createdTime: "2023-09-11T14:30:00",
    reaction: {
      likeCount: 2,
    },
    content:
      '[{"id":"aff887cc-b2df-43b4-a89d-1c19e77adb08","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[{"type":"text","text":"2222","styles":{}}],"children":[]},{"id":"c8b0b593-80db-4555-bf7a-81fc0ee4200c","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[],"children":[]}]',
  },
];

const COMMENTS_BOOKMARK_ID_4 = [
  {
    commentId: 4,
    author: {
      memberId: 1,
      nickname: "뭐당가이름이",
      profileImgUrl: "www.asdjfk.com",
    },
    createdTime: "2023-09-11T14:30:00",
    reaction: {
      likeCount: 2,
    },
    content:
      '[{"id":"aff887cc-b2df-43b4-a89d-1c19e77adb08","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[{"type":"text","text":"333","styles":{}}],"children":[]},{"id":"c8b0b593-80db-4555-bf7a-81fc0ee4200c","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[],"children":[]}]',
  },
  {
    commentId: 5,
    author: {
      memberId: 6,
      nickname: "랄랄라",
      profileImgUrl: "www.asdjfk.com",
    },
    createdTime: "2023-09-11T14:30:00",
    reaction: {
      likeCount: 2,
    },
    content:
      '[{"id":"aff887cc-b2df-43b4-a89d-1c19e77adb08","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[{"type":"text","text":"4444","styles":{}}],"children":[]},{"id":"c8b0b593-80db-4555-bf7a-81fc0ee4200c","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[],"children":[]}]',
  },
];

const COMMENTS_BOOKMARK_ID_5 = [] as any;

export const COMMENTS = [...COMMENTS_BOOKMARK_ID_3, ...COMMENTS_BOOKMARK_ID_4];

export const COMMENTS_MAP = new Map()
  .set("3", COMMENTS_BOOKMARK_ID_3)
  .set("4", COMMENTS_BOOKMARK_ID_4)
  .set("5", COMMENTS_BOOKMARK_ID_5);

export const BOOKMARKS = [
  {
    bookmarkId: 3,
    author: {
      memberId: 1,
      nickname: "뭐당가이름이",
      profileImgUrl: "www.asdjfk.com",
    },
    createdTime: "2023-09-11T14:30:00",
    reaction: {
      likeCount: 2,
    },
    content:
      '[{"id":"aff887cc-b2df-43b4-a89d-1c19e77adb08","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[{"type":"text","text":"1","styles":{}}],"children":[]},{"id":"c8b0b593-80db-4555-bf7a-81fc0ee4200c","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[],"children":[]}]',
    commentCount: 5,
    page: 24,
  },
  {
    bookmarkId: 4,
    author: {
      memberId: 1,
      nickname: "뭐당가이름이",
      profileImgUrl: "www.asdjfk.com",
    },
    createdTime: "2023-09-11T14:30:00",
    reaction: {
      likeCount: 2,
    },
    content:
      '[{"id":"aff887cc-b2df-43b4-a89d-1c19e77adb08","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[{"type":"text","text":"2","styles":{}}],"children":[]},{"id":"c8b0b593-80db-4555-bf7a-81fc0ee4200c","type":"paragraph","props":{"textColor":"default","backgroundColor":"default","textAlignment":"left"},"content":[],"children":[]}]',
    commentCount: 5,
  },
];

export const CHAPTER_LIST = [
  {
    chapterId: 15,
    statusId: 1,
    title: "K는 대한민국이 아니다",
    recentBookmark: {
      authorProfileImgUrl: "profileImgUrl",
      content: "asdfsd",
    },
    topics: [
      {
        topicId: 52,
        title: "K 프리미엄, 국적은 사라지고 스타일은 남아",
        recentBookmark: {
          authorProfileImgUrl: "profileImgUrl",
          content: "asdfsd",
        },
      },
      {
        topicId: 552,
        title: "‘서울러’라는 소속감 혹은 구별 짓기",
        recentBookmark: {
          authorProfileImgUrl: "profileImgUrl",
          content: "asdfsd",
        },
      },
    ],
  },
  {
    chapterId: 155,
    statusId: 3,
    title: "chapter 1",
    recentBookmark: {
      authorProfileImgUrl: "profileImgUrl",
      content: "asdfsd",
    },
    topics: [
      {
        topicId: 532,
        title: "chapter 1의 토픽 1의 제목",
        recentBookmark: {
          authorProfileImgUrl: "profileImgUrl",
          content: "asdfsd",
        },
      },
      {
        topicId: 5525,
        title: "chapter 1의 토픽 2의 제목",
        recentBookmark: {
          authorProfileImgUrl: "profileImgUrl",
          content: "asdfsd",
        },
      },
    ],
  },
  {
    chapterId: 16,
    statusId: 2,
    title: "chapter 2",
    recentBookmark: {
      authorProfileImgUrl: "profileImgUrl",
      content: "asdfsd",
    },
    topics: [
      {
        topicId: 55,
        title: "chapter 2의 토픽 1의 제목",
        recentBookmark: {
          authorProfileImgUrl: "profileImgUrl",
          content: "asdfsd",
        },
      },
    ],
  },
];

export const BOOK_CLUB_DETAIL_OPEN = {
  name: "내가만든북클럽",
  profileImgUrl: "https://avatars.githubusercontent.com/u/56245755?v=4",
  members: [
    {
      id: 1,
      nickname: "gamgyul",
      email: "gamgyul@gmail.com",
      profileImgUrl: "https://avatars.githubusercontent.com/u/56245755?v=4",
    },
    {
      id: 2,
      nickname: "nag",
      email: "gamgyul@gmail.com",
      profileImgUrl: "https://avatars.githubusercontent.com/u/56245755?v=4",
    },
  ],
  lastBook: {
    name: "하늘과 바람과 별과 인간",
    author: "김상욱",
  },
  createdTime: "2023-08-30T10:00:00",
  status: "open",
  upcomingGatheringDate: "2023-11-11T10:30:00",
};

export const USER_BOOK_LIST = [
  {
    id: 1,
    statusId: 2,
    isbn: "9788966262335",
    bookClub: {
      id: 1,
      name: "낙감귤조이",
    },
    title: "함께 자라기 - 애자일로 가는 길",
    cover:
      "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
    author: "김창준 (지은이)",
    category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
  },
  {
    id: 2,
    statusId: 3,
    isbn: "9788966262335",
    bookClub: {
      id: 1,
      name: "낙감귤조이",
    },
    title: "함께 자라기 - 애자일로 가는 길",
    cover:
      "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
    author: "김창준 (지은이)",
    category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
  },
  {
    id: 3,
    statusId: 4,
    isbn: "9788991268807",
    bookClub: {
      id: 1,
      name: "낙감귤조이",
    },
    title: "프로그래머의 길, 멘토에게 묻다",
    cover:
      "https://image.aladin.co.kr/product/741/54/coversum/8991268803_1.jpg",
    author: "데이브 후버, 애디웨일 오시나이 (지은이), 강중빈 (옮긴이)",
    category:
      "국내도서>컴퓨터/모바일>프로그래밍 개발/방법론>프로그래밍 기초/개발 방법론",
  },
  {
    id: 4,
    isbn: "9791168341364",
    bookClub: {
      id: 1,
      name: "낙감귤조이",
    },
    title:
      "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
    cover:
      "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
    author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
    category: "국내도서>인문학>철학 일반>교양 철학",
  },
  {
    id: 5,
    isbn: "9791130645278",
    bookClub: {
      id: 3,
      name: "내꿈은농부!",
    },
    title:
      "아무도 가지 않은 길에 부가 있었다 - 흔들리는 투자자를 위한 부자의 독설 41",
    cover:
      "https://image.aladin.co.kr/product/32265/15/coversum/k722834840_1.jpg",
    author: "정민우(달천) (지은이)",
    category: "국내도서>경제경영>재테크/투자>재테크/투자 일반",
  },
  {
    id: 11,
    isbn: "9788966262335",
    bookClub: {
      id: 1,
      name: "낙감귤조이",
    },
    title: "함께 자라기 - 애자일로 가는 길",
    cover:
      "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
    author: "김창준 (지은이)",
    category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
  },
  {
    id: 12,
    isbn: "9788966262335",
    bookClub: {
      id: 1,
      name: "낙감귤조이",
    },
    title: "함께 자라기 - 애자일로 가는 길",
    cover:
      "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
    author: "김창준 (지은이)",
    category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
  },
  // {
  //   id: 13,
  //   isbn: "9788991268807",
  //   bookClub: {
  //     id: 1,
  //     name: "낙감귤조이",
  //   },
  //   title: "프로그래머의 길, 멘토에게 묻다",
  //   cover:
  //     "https://image.aladin.co.kr/product/741/54/coversum/8991268803_1.jpg",
  //   author: "데이브 후버, 애디웨일 오시나이 (지은이), 강중빈 (옮긴이)",
  //   category:
  //     "국내도서>컴퓨터/모바일>프로그래밍 개발/방법론>프로그래밍 기초/개발 방법론",
  // },
  // {
  //   id: 14,
  //   isbn: "9791168341364",
  //   bookClub: {
  //     id: 1,
  //     name: "낙감귤조이",
  //   },
  //   title:
  //     "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
  //   cover:
  //     "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
  //   author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
  //   category: "국내도서>인문학>철학 일반>교양 철학",
  // },
  // {
  //   id: 15,
  //   isbn: "9791130645278",
  //   bookClub: {
  //     id: 3,
  //     name: "내꿈은농부!",
  //   },
  //   title:
  //     "아무도 가지 않은 길에 부가 있었다 - 흔들리는 투자자를 위한 부자의 독설 41",
  //   cover:
  //     "https://image.aladin.co.kr/product/32265/15/coversum/k722834840_1.jpg",
  //   author: "정민우(달천) (지은이)",
  //   category: "국내도서>경제경영>재테크/투자>재테크/투자 일반",
  // },
  // {
  //   id: 21,
  //   isbn: "9788966262335",
  //   bookClub: {
  //     id: 1,
  //     name: "낙감귤조이",
  //   },
  //   title: "함께 자라기 - 애자일로 가는 길",
  //   cover:
  //     "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
  //   author: "김창준 (지은이)",
  //   category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
  // },
  // {
  //   id: 22,
  //   isbn: "9788966262335",
  //   bookClub: {
  //     id: 1,
  //     name: "낙감귤조이",
  //   },
  //   title: "함께 자라기 - 애자일로 가는 길",
  //   cover:
  //     "https://image.aladin.co.kr/product/17597/74/coversum/8966262333_1.jpg",
  //   author: "김창준 (지은이)",
  //   category: "국내도서>컴퓨터/모바일>컴퓨터 공학>소프트웨어 공학",
  // },
  // {
  //   id: 23,
  //   isbn: "9788991268807",
  //   bookClub: {
  //     id: 1,
  //     name: "낙감귤조이",
  //   },
  //   title: "프로그래머의 길, 멘토에게 묻다",
  //   cover:
  //     "https://image.aladin.co.kr/product/741/54/coversum/8991268803_1.jpg",
  //   author: "데이브 후버, 애디웨일 오시나이 (지은이), 강중빈 (옮긴이)",
  //   category:
  //     "국내도서>컴퓨터/모바일>프로그래밍 개발/방법론>프로그래밍 기초/개발 방법론",
  // },
  // {
  //   id: 24,
  //   isbn: "9791168341364",
  //   bookClub: {
  //     id: 1,
  //     name: "낙감귤조이",
  //   },
  //   title:
  //     "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
  //   cover:
  //     "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
  //   author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
  //   category: "국내도서>인문학>철학 일반>교양 철학",
  // },
  // {
  //   id: 25,
  //   isbn: "9791130645278",
  //   bookClub: {
  //     id: 3,
  //     name: "내꿈은농부!",
  //   },
  //   title:
  //     "아무도 가지 않은 길에 부가 있었다 - 흔들리는 투자자를 위한 부자의 독설 41아무도 가지 않은 길에 부가 있었다 - 흔들리는 투자자를 위한 부자의 독설 41",
  //   cover:
  //     "https://image.aladin.co.kr/product/32265/15/coversum/k722834840_1.jpg",
  //   author: "정민우(달천) (지은이)",
  //   category: "국내도서>경제경영>재테크/투자>재테크/투자 일반",
  // },
];

export const BOOK_CLUB_LIST = [
  {
    id: 1,
    creatorId: 1,
    name: "내가만든북클럽",
    status: "open",
    profileImgUrl:
      "https://frtest12.s3.ap-northeast-2.amazonaws.com/profile-image/1",
    lastBook: {
      name: "책이름",
      author: "저자이름",
    },
    createdTime: "2023-08-30T10:00:0000",
    upcomingGatheringDate: "2023-11-11T10:30:0000",
    members: [
      {
        id: 1,
        nickname: "gamgyul",
        profileImgUrl: "http://naver.com",
      },
      {
        id: 2,
        nickname: "nag",
        profileImgUrl: "http://naver.com",
      },
    ],
  },
  {
    id: 2,
    creatorId: 1,
    name: "내가만든북클럽2",
    status: "closed",
    profileImgUrl:
      "https://frtest12.s3.ap-northeast-2.amazonaws.com/profile-image/2",
    lastBook: {
      name: "책이름",
      author: "저자이름",
    },
    createdTime: "2023-08-30",
    closedTime: "2023-10-15",
    members: [
      {
        id: 1,
        nickname: "gamgyul",
        profileImgUrl: "https://avatars.githubusercontent.com/u/56245755?v=4",
      },
      {
        id: 2,
        nickname: "nag",
        profileImgUrl: "https://avatars.githubusercontent.com/u/56245755?v=4",
      },
    ],
  },
];

export const MEMBER_INFO = {
  id: 1,
  nickname: "감귤차먹고아프지마세요",
  email: "gamgyul@gmail.com",
  profileImgUrl: "https://avatars.githubusercontent.com/u/56245755?v=4",
};

export const ALADIN_BOOK_SEARCH_EXAMPLE = [
  {
    title:
      "우리 인생에 바람을 초대하려면 - 세계적 지성이 들려주는 모험과 발견의 철학",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=326109231&amp;partner=openAPI&amp;start=api",
    author: "파스칼 브뤼크네르 (지은이), 이세진 (옮긴이)",
    pubDate: "2023-10-16",
    description:
      "메디치상 &#8231; 르노도상 &#8231; 몽테뉴상 &#8231; 뒤메닐상 수상에 빛나는 세계적 지성 파스칼 브뤼크네르의 신작. 무기력의 시대에 새로운 가능성을 이야기하는 모험과 발견의 철학. “당신의 삶은 더욱 경이로워야 합니다”",
    isbn: "9791168341364",
    cover:
      "https://image.aladin.co.kr/product/32610/92/coversum/k112935541_1.jpg",
    categoryName: "국내도서>인문학>철학 일반>교양 철학",
    publisher: "인플루엔셜(주)",
  },
  {
    title: "하늘과 바람과 별과 인간 - 원자에서 인간까지",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=316008497&amp;partner=openAPI&amp;start=api",
    author: "김상욱 (지은이)",
    pubDate: "2023-05-26",
    description:
      "기본 입자와 원자에서 시작해 존재의 층위들을 하나하나씩 밟아가며 물질과 생명, 그리고 더 나아가 우주와 인간이 어떻게 서로 연결되어 있는지 조망하며 차갑게만 느껴지던 우주가 물리학자의 시선 속에서 얼마나 따뜻할 수 있는지 보여준다.",
    isbn: "9791166891496",
    cover:
      "https://image.aladin.co.kr/product/31600/84/coversum/k612833060_1.jpg",

    categoryName: "국내도서>과학>기초과학/교양과학",
    publisher: "바다출판사",
  },
  {
    title:
      "태도, 믿음을 말하다 - 나와 이웃과 하나님을 대하는 바람직한 태도에 관하여",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=326338884&amp;partner=openAPI&amp;start=api",
    author: "조명신 (지은이)",
    pubDate: "2023-10-20",
    description:
      "조명신 목사는 세심한 눈으로 일상과 주변을 관찰하고 그 안에서 발견한 이야기를 차분히 글로 풀어나간다. 이 책에서도 저자는 하나님 사랑과 이웃 사랑이 하나임을 보여 주는 ‘선한 사마리아인의 비유’가 전하듯 하나님 사랑을 자부하는 이들에게 이웃 사랑이 실천되어야 진정한 하나님 사랑임을 자신의 경험과 일상을 통해 따뜻하게 전하고 있다.",

    isbn: "9791198456731",
    cover:
      "https://image.aladin.co.kr/product/32633/88/coversum/k672935553_1.jpg",
    categoryName:
      "국내도서>종교/역학>기독교(개신교)>기독교(개신교) 신앙생활>신앙생활일반",
    publisher: "죠이북스",
  },
  {
    title:
      "초판본 하늘과 바람과 별과 詩 - 윤동주 유고시집, 1955년 10주기 기념 증보판",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=72369420&amp;partner=openAPI&amp;start=api",
    author: "윤동주 (지은이)",
    pubDate: "2016-01-30",
    description:
      "별이 된 유고시집 &lt;하늘과 바람과 별과 詩&gt; 1955년 10주기 기념 증보판. '서시', '별 헤는 밤', '십자가' 등 주옥같은 시 31편이 수록된 초판본에 유족들이 보관하고 있던 원고를 더해 서거 10주기를 기념하여 1955년 발행된 이 증보판에는 윤동주의 뜨거운 마음이 고스란히 담겨 있다.",

    isbn: "9788998046682",

    cover:
      "https://image.aladin.co.kr/product/7236/94/coversum/s632636015_1.jpg",

    categoryName: "국내도서>소설/시/희곡>시>한국시",
    publisher: "소와다리",
  },
  {
    title: "드라큘라의 시",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=324465620&amp;partner=openAPI&amp;start=api",
    author: "김개미 (지은이), 경자 (그림)",
    pubDate: "2023-09-11",
    description:
      "바람동시책 4권 시를 품은 이야기이자 이야기가 있는 바람동시책 시리즈로, 혼자 살아가는 드라큘라 아이의 외로움과 그리움을 이야기한다. 혼자 살아가는 드라큘라 아이를 통해, 어른들이 무심코 지나칠 수 있는 아이들의 외로움과 두려움을 들여다본다.",

    isbn: "9791165734299",
    cover:
      "https://image.aladin.co.kr/product/32446/56/coversum/k402935294_1.jpg",
    categoryName: "국내도서>어린이>초등 전학년>동시/동요",
    publisher: "천개의바람",
  },
  {
    title: "바람공방의 마음에 드는 니트",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=322639357&amp;partner=openAPI&amp;start=api",
    author: "바람공방 (지은이), 남궁가윤 (옮긴이)",
    pubDate: "2023-08-17",
    description:
      "세계적으로 유명한 뜨개 디자이너 ‘바람공방’! 우리나라에서는 ‘가제코보’라는 이름으로 잘 알려져 있다. 이 책에서는 바람공방이 사심을 듬뿍 담아, 세련된 무늬, 예쁜 색의 실로 디자인한 니트웨어와 소품을 만나볼 수 있다.",
    isbn: "9791160079494",
    cover:
      "https://image.aladin.co.kr/product/32263/93/coversum/k232834849_1.jpg",
    categoryName: "국내도서>건강/취미>공예>뜨개질/퀼트/십자수/바느질",
    publisher: "한즈미디어(한스미디어)",
  },
  {
    title: "숨결이 바람 될 때 - 서른여섯 젊은 의사의 마지막 순간",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=89928183&amp;partner=openAPI&amp;start=api",
    author: "폴 칼라니티 (지은이), 이종인 (옮긴이)",
    pubDate: "2016-08-19",
    description:
      "신경외과 의사로서 치명적인 뇌 손상 환자들을 치료하며 죽음과 싸우다가 자신도 폐암 말기 판정을 받고 죽음을 마주하게 된 서른여섯 젊은 의사 폴 칼라니티의 마지막 2년의 기록. 출간 즉시 아마존과 「뉴욕타임스」 베스트셀러 1위에 올랐으며 12주 연속으로 뉴욕타임스 베스트셀러 1위 자리를 지켰다.",
    isbn: "9788965961956",
    cover:
      "https://image.aladin.co.kr/product/8992/81/coversum/8965961955_1.jpg",
    categoryName: "국내도서>에세이>외국에세이",
    publisher: "흐름출판",
  },
  {
    title: "푸른 사자 와니니 5 - 초원의 바람",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=300262898&amp;partner=openAPI&amp;start=api",
    author: "이현 (지은이), 오윤화 (그림)",
    pubDate: "2022-08-19",
    description:
      "전편에서 친구들과 무리를 이루고 어엿한 우두머리로 거듭난 와니니가 엄마가 되어 돌아왔다. 갓 태어난 새끼들과 행복한 나날을 보내던 와니니는 불의의 사고로 큰 슬픔을 겪는다.",
    isbn: "9788936443269",
    cover:
      "https://image.aladin.co.kr/product/30026/28/coversum/8936443267_1.jpg",
    categoryName: "국내도서>어린이>동화/명작/고전>국내창작동화",
    publisher: "창비",
  },
  {
    title: "사방에 부는 바람",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=324469758&amp;partner=openAPI&amp;start=api",
    author: "크리스틴 해나 (지은이), 박찬원 (옮긴이)",
    pubDate: "2023-09-14",
    description:
      "주목받지 못한 역사를 무대로 가장 사랑받는 이야기를 선보여온 미국의 밀리언셀러 작가 크리스틴 해나의 신작. 저자는 우리를 1930년대 대공황기, 먼지 폭풍에 휩싸인 텍사스 대평원으로 이끈다. 고난의 시대를 살아낸 한 여성의 삶을 그리며, 저자는 놀랍도록 풍성하게 역사를 증언한다.",
    isbn: "9791167373533",
    cover:
      "https://image.aladin.co.kr/product/32446/97/coversum/k172935295_1.jpg",
    categoryName: "국내도서>소설/시/희곡>영미소설",
    publisher: "은행나무",
  },
  {
    title: "바람이 분다 당신이 좋다 - 이병률 여행산문집",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=17563850&amp;partner=openAPI&amp;start=api",
    author: "이병률 (지은이)",
    pubDate: "2012-07-04",
    description:
      "이병률 여행산문집. &lt;끌림&gt; 두 번째 이야기. 길 위에서 쓰고 찍은 사람과 인연, 사랑. 작가의 이 여행노트는 오래전부터 계획된 여행기가 아니라, 소소하지만 낯선 여행지에서의 일상과 그리고 주변의 사람들 이야기 날것 그대로임을 알게 해준다.",
    isbn: "9788993928488",
    cover:
      "https://image.aladin.co.kr/product/1756/38/coversum/8993928487_2.jpg",
    categoryName: "국내도서>에세이>한국에세이",
    publisher: "달",
  },
  {
    title: "꽃샘바람에 흔들린다면 너는 꽃",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=291817995&amp;partner=openAPI&amp;start=api",
    author: "류시화 (지은이)",
    pubDate: "2022-04-11",
    description:
      "류시화 시인이 10년 만에 내놓는 시집. 삶 속에서 심호흡이 필요할 때 가슴으로 암송하는 시, 세계를 내면에서 보고 마음속 불을 기억하게 해 주는 시 70편이 실렸다. 섬세한 언어 감각, 자유로운 시적 상상력이 빛난다.",

    isbn: "9791190382618",

    cover:
      "https://image.aladin.co.kr/product/29181/79/coversum/k712837984_1.jpg",

    categoryName: "국내도서>소설/시/희곡>시>한국시",
    publisher: "수오서재",
  },
  {
    title:
      "할머니의 용궁 여행 - 2021 경남독서한마당 선정도서, 2021 국가인권위원회 인권도서관 어린이인권도서 목록 추천도서, 2021 읽어주기좋은책 선정도서, 2020 문학나눔 선정도서",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=236783585&amp;partner=openAPI&amp;start=api",
    author: "권민조 (지은이)",
    pubDate: "2020-04-07",
    description:
      "바람 그림책 91권. 아윤이의 할머니는 경상도 바닷가 마을의 해녀이다. 할머니가 물질하러 바다로 나가면 아윤이는 집에서 할머니를 기다린다. 그러던 어느 날, 할머니가 늦게까지 돌아오지 않자 아윤이는 할머니가 걱정되어 바닷가로 가는데….",

    isbn: "9791190077439",

    cover:
      "https://image.aladin.co.kr/product/23678/35/coversum/k442638935_1.jpg",

    categoryName: "국내도서>유아>그림책>나라별 그림책>한국 그림책",
    publisher: "천개의바람",
  },
  {
    title:
      "패배 히로인과 내가 사귄다고 주변 사람들이 착각하는 바람에 소꿉친구와 수라장이 되었다 2 - S Novel+",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=323730891&amp;partner=openAPI&amp;start=api",
    author: "네코쿠로 (지은이), piyopoyo (그림), 한수진 (옮긴이)",
    pubDate: "2023-09-14",
    description:
      "교내 2대 미소녀 중 한 명이자 패배 히로인인 아키미 마린과 자신의 소꿉친구인 네모토 카스미, 이 두 소녀와의 사랑 전쟁에 휘말려든 하자쿠라 요우. 마린은 갑자기 요우를 성이 아닌 이름으로 부르고 싶다고 조르고, 또 사이가 멀어졌던 카스미는 ‘예뻐해 달라’고 요구하면서 방에서 요우를 유혹하기도 하는데….",

    isbn: "9791138480116",

    cover:
      "https://image.aladin.co.kr/product/32373/8/coversum/k072935570_1.jpg",

    categoryName: "국내도서>소설/시/희곡>라이트 노벨>S 노벨",
    publisher: "㈜소미미디어",
  },
  {
    title:
      "윤동주 전 시집‘하늘과 바람과 별과 시’(양장) - 서거 77주년, 탄생 105주년 기념 '하늘과 바람과 별과 시' 뉴 에디션",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=289183005&amp;partner=openAPI&amp;start=api",
    author: "윤동주 (지은이), 윤동주 100년 포럼 (엮은이)",
    pubDate: "2022-02-16",
    description:
      "스테디셀러 &lt;윤동주 전 시집&gt;의 고급양장 뉴 에디션. 시인의 작품 전체를 비롯해 발문 및 후기까지를 모두 발굴하여 한 권에 담았다. 발간 당시 시가 추가될 때마다 실린 추모 글들을 마지막 8장에 모아 독자들 누구나 쉽고 편하게 읽을 수 있고 이해할 수 있도록 정성들여 편집했다.",

    isbn: "9791157956357",

    cover:
      "https://image.aladin.co.kr/product/28918/30/coversum/k302836017_1.jpg",

    categoryName: "국내도서>소설/시/희곡>시>한국시",
    publisher: "스타북스",
  },
  {
    title: "홍길동전 : 춤추는 소매 바람을 따라 휘날리니",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=16068367&amp;partner=openAPI&amp;start=api",
    author: "류수열 (지은이), 이승민 (그림)",
    pubDate: "2012-03-15",
    description:
      "'국어시간에 고전읽기' 시리즈 세 번째 책으로, 고전소설 홍길동전의 고어체를 군더더기 없이 매끈한 현대어로 바꿨다. 여러 판본 중에서 사회비판의식이 분명한 완판본을 원본으로 삼았으며, 순조23년(1823년)에 일어났던 서얼유생들의 집단상소 사건을 역사신문으로 꾸민 '서얼 신문' 등 풍성한 읽을거리도 싣고 있다.",

    isbn: "9788996851516",

    cover:
      "https://image.aladin.co.kr/product/1606/83/coversum/6000539774_1.jpg",

    categoryName: "국내도서>청소년>청소년 고전",
    publisher: "나라말",
  },
  {
    title: "바람의 딸, 우리 땅에 서다 - 개정판",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=211073&amp;partner=openAPI&amp;start=api",
    author: "한비야 (지은이)",
    pubDate: "2006-09-08",
    description:
      "한비야의 <바람의 딸, 우리 땅에 서다>가 7년 만에 새로운 모습으로 출간되었다. 이 책은 7년간 현대문명의 손길이 닿지 않는 전 세계 65개국의 오지를 찾아다녔던 한비야가 전라남도 해남 땅끝마을에서 강원도 통일전망대까지 800km(2,000리)에 이르는 우리 땅을 49일간 두 발로 걸으며 쓴 국토종단기이다.",

    isbn: "9788971844755",

    cover: "https://image.aladin.co.kr/product/21/10/coversum/8971844752_2.jpg",

    categoryName: "국내도서>여행>국내 여행에세이",
    publisher: "푸른숲",
  },
  {
    title: "개마법사 쿠키와 월요일의 달리기",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=324047086&amp;partner=openAPI&amp;start=api",
    author: "이승민 (지은이), 조승연 (그림)",
    pubDate: "2023-09-04",
    description:
      "바람어린이책 24권. 개마법사 쿠키 시리즈 2번째 이야기. 어느 날 고양이 강이가 사료를 먹지 않았다. 어디 아픈가 싶어 이순례 할머니로 변신해서 동물병원에 데려갔다. 그런데 수의사 선생님은 강이가 아주 건강하다고 했는데….",

    isbn: "9791165734282",

    cover:
      "https://image.aladin.co.kr/product/32404/70/coversum/k642935981_1.jpg",

    categoryName: "국내도서>어린이>동화/명작/고전>국내창작동화",
    publisher: "천개의바람",
  },
  {
    title: "사자마트",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=313959582&amp;partner=openAPI&amp;start=api",
    author: "김유 (지은이), 소복이 (그림)",
    pubDate: "2023-03-28",
    description:
      "바람그림책 137권. 김유 작가와 소복이 작가가 다시 뭉친 두 번째 그림책이다. 아파트 상가에 사자 씨가 ‘사자마트’를 열었다. 사자 씨의 이름이면서, 사람들이 물건을 많이 사러 오길 바라는 마음을 담은 마트였다. 한 아주머니가 사자마트에 들어섰는데….",

    isbn: "9791165734091",

    cover:
      "https://image.aladin.co.kr/product/31395/95/coversum/k282832105_2.jpg",

    categoryName: "국내도서>유아>그림책>나라별 그림책>한국 그림책",
    publisher: "천개의바람",
  },
  {
    title:
      "마음버스 - 2022 서울 강남구·종로구·서대문구 올해의 한 책 선정, 2022 한국학교사서협회 추천",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=291122693&amp;partner=openAPI&amp;start=api",
    author: "김유 (지은이), 소복이 (그림)",
    pubDate: "2022-03-25",
    description:
      "바람그림책 122권. 마을버스에서 ㄹ이 사라졌다. 운전사 아저씨는 휑해 보이지 않도록 나무 창틀을 걸었다. 그리고 버스를 몰아 정류장으로 갔다. 손님들이 하나둘 버스에 올라탔다. 손님들은 날마다 보는 사이였지만 서로 말없이 창밖만 보았는데….",

    isbn: "9791165732257",

    cover:
      "https://image.aladin.co.kr/product/29112/26/coversum/k822837265_1.jpg",

    categoryName: "국내도서>유아>그림책>나라별 그림책>한국 그림책",
    publisher: "천개의바람",
  },
  {
    title: "하늘과 바람과 별과 시 (미니북)",
    link: "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=322082704&amp;partner=openAPI&amp;start=api",
    author: "윤동주 (지은이)",
    pubDate: "2023-07-31",
    description:
      "윤동주는 식민 통치의 암울한 현실 속에서도 민족에 대한 사랑과 독립에 대한 간절한 소망을 서정적인 시어에 담은 민족시인이다. 그는 기독교 정신과 독립에 대한 열망, 투사가 되지 못하는 자괴감과 아이들의 눈높이로 본 세상에 대한 묘사까지도 그의 시에 녹여냈다.",

    isbn: "9791164457649",

    cover:
      "https://image.aladin.co.kr/product/32208/27/coversum/k832834526_1.jpg",

    categoryName: "국내도서>소설/시/희곡>시>한국시",
    publisher: "더클래식",
  },
];

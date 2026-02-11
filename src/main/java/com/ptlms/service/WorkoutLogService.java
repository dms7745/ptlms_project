package com.ptlms.service;

import com.ptlms.entity.Member;
import com.ptlms.entity.User;
import com.ptlms.entity.WorkoutLog;
import com.ptlms.entity.WorkoutPart;
import com.ptlms.repository.MemberRepository;
import com.ptlms.repository.WorkoutLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;
    private final MemberRepository memberRepository;

    // ★ 오운완 등록 (회원 전용)
    @Transactional
    public WorkoutLog createWorkoutLog(Long memberId, LocalDate workoutDate, WorkoutPart workoutPart,
                                        String workoutDetail, Integer sets, Integer durationMinutes,
                                        String dietPhotoUrl, String dietMemo) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        WorkoutLog log = WorkoutLog.builder()
                .member(member)
                .workoutDate(workoutDate)
                .workoutPart(workoutPart)
                .workoutDetail(workoutDetail)
                .sets(sets)
                .durationMinutes(durationMinutes)
                .dietPhotoUrl(dietPhotoUrl)
                .dietMemo(dietMemo)
                .completed(false)
                .build();

        return workoutLogRepository.save(log);
    }

    // ★ 오운완 완료 처리 (회원 전용)
    @Transactional
    public WorkoutLog completeWorkout(Long logId) {
        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("운동 기록을 찾을 수 없습니다."));

        log.setCompleted(true);
        return workoutLogRepository.save(log);
    }

    // ★ 본인 운동 기록 조회 (회원 전용)
    public List<WorkoutLog> getMyWorkoutLogs(Long memberId) {
        return workoutLogRepository.findByMemberId(memberId);
    }

    // 기간별 운동 기록 (달력용)
    public List<WorkoutLog> getWorkoutLogsByDateRange(Long memberId, LocalDate start, LocalDate end) {
        return workoutLogRepository.findByMemberIdAndDateRange(memberId, start, end);
    }

    // 날짜별 운동 기록
    public List<WorkoutLog> getWorkoutLogsByDate(Long memberId, LocalDate date) {
        return workoutLogRepository.findByMemberIdAndWorkoutDate(memberId, date);
    }

    // ★ 트레이너 피드백 작성 (트레이너 전용)
    @Transactional
    public WorkoutLog addTrainerFeedback(Long logId, String feedback) {
        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("운동 기록을 찾을 수 없습니다."));

        log.setTrainerFeedback(feedback);
        return workoutLogRepository.save(log);
    }

    // ★ 담당 회원들의 최근 기록 조회 (트레이너 전용)
    public List<WorkoutLog> getRecentLogsByTrainer(Long trainerId) {
        return workoutLogRepository.findRecentByTrainerId(trainerId);
    }

    // 운동 기록 수정 (트레이너 전용)
    @Transactional
    public WorkoutLog updateWorkoutLog(Long logId, WorkoutPart workoutPart, String workoutDetail,
                                        Integer sets, Integer durationMinutes) {
        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("운동 기록을 찾을 수 없습니다."));

        if (workoutPart != null) log.setWorkoutPart(workoutPart);
        if (workoutDetail != null) log.setWorkoutDetail(workoutDetail);
        if (sets != null) log.setSets(sets);
        if (durationMinutes != null) log.setDurationMinutes(durationMinutes);

        return workoutLogRepository.save(log);
    }
}
